/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 socraticphoenix@gmail.com
 * Copyright (c) 2017 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gmail.socraticphoenix.asmsig.visitor;

import com.gmail.socraticphoenix.asmsig.type.TypeArray;
import com.gmail.socraticphoenix.asmsig.type.TypeFill;
import com.gmail.socraticphoenix.asmsig.type.TypeInformal;
import com.gmail.socraticphoenix.asmsig.type.TypeVarRef;
import com.gmail.socraticphoenix.asmsig.type.TypeWild;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;

import java.util.Stack;
import java.util.function.Consumer;

/**
 * A {@link SignatureVisitor} which builds a {@link TypeInformal}.
 */
public class TypeSignatureVisitor extends SignatureVisitor implements Opcodes {
    private Stack<TypeSignatureVisitor> visitors;

    private Consumer<TypeInformal> end;
    private TypeInformal type;

    /**
     * Creates a new TypeSignatureVisitor with the given action to execute upon completion.
     *
     * @param end The action to execute upon completion.
     */
    public TypeSignatureVisitor(Consumer<TypeInformal> end) {
        super(ASM5);
        this.end = end;
        this.type = new TypeFill(Type.getType(Object.class));
        this.visitors = new Stack<>();
    }

    /**
     * Creates a new TypeSignatureVisitor
     */
    public TypeSignatureVisitor() {
        this(null);
    }

    @Override
    public void visitBaseType(char descriptor) {
        this.type = new TypeFill(Type.getType(String.valueOf(descriptor)));
    }

    @Override
    public void visitTypeVariable(String name) {
        this.type = new TypeVarRef(name);
    }

    @Override
    public SignatureVisitor visitArrayType() {
        this.type = new TypeArray(new TypeFill(Type.getType(Object.class)));
        return logAndReturn(new TypeSignatureVisitor(f -> ((TypeArray) this.type).setType(f)));
    }

    @Override
    public void visitClassType(String name) {
        this.type = new TypeFill(Type.getObjectType(name));
    }

    @Override
    public void visitInnerClassType(String name) {
        this.type = ((TypeFill) this.type).asInner(Type.getObjectType(name));
    }

    @Override
    public void visitTypeArgument() {
        ((TypeFill) this.type).addPart(new TypeWild(null, null));
    }

    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        if(wildcard != '=') {
            return logAndReturn(new TypeSignatureVisitor(f -> {
                TypeWild wild = new TypeWild(null, null);
                if(wildcard == '-') {
                    wild.setLower(f);
                } else {
                    wild.setUpper(f);
                }
                ((TypeFill) this.type).addPart(wild);
            }));
        } else {
            return logAndReturn(new TypeSignatureVisitor(f -> ((TypeFill) this.type).addPart(f)));
        }
    }

    @Override
    public void visitEnd() {
        while (!this.visitors.isEmpty()) {
            this.visitors.pop().visitEnd();
        }

        if(this.end != null) {
            this.end.accept(this.type);
            this.end = null;
        }
        super.visitEnd();
    }

    private TypeSignatureVisitor logAndReturn(TypeSignatureVisitor visitor) {
        this.visitors.push(visitor);
        return visitor;
    }

    /**
     * @return The built type signature.
     */
    public TypeInformal getSignature() {
        return this.type;
    }

}
