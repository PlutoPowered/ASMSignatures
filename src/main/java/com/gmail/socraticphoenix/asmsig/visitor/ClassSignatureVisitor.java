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

import com.gmail.socraticphoenix.asmsig.signature.ClassSignature;
import com.gmail.socraticphoenix.asmsig.type.TypeFill;
import com.gmail.socraticphoenix.asmsig.type.TypeParameterized;
import com.gmail.socraticphoenix.asmsig.type.TypeVar;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;

/**
 * A {@link SignatureVisitor} which builds a {@link ClassSignature}.
 */
public class ClassSignatureVisitor extends SignatureVisitor implements Opcodes {
    private ClassSignature signature;
    private TypeVar previous;

    /**
     * Creates a new ClassSignatureVisitor
     */
    public ClassSignatureVisitor() {
        super(ASM5);
        this.signature = new ClassSignature(new TypeParameterized(Type.getType(Object.class)), new TypeFill(Type.getType(Object.class)));
    }

    @Override
    public void visitFormalTypeParameter(String name) {
        this.previous = new TypeVar(name);
        this.signature.getType().addParameter(this.previous);
    }

    @Override
    public SignatureVisitor visitClassBound() {
        return new TypeSignatureVisitor(f -> this.previous.setClassBound(f));
    }

    @Override
    public SignatureVisitor visitInterfaceBound() {
        return new TypeSignatureVisitor(f -> this.previous.addInterBound(f));
    }

    @Override
    public SignatureVisitor visitSuperclass() {
        return new TypeSignatureVisitor(f -> this.signature.setSuperclass((TypeFill) f));
    }

    @Override
    public SignatureVisitor visitInterface() {
        return new TypeSignatureVisitor(f -> this.signature.addInterface((TypeFill) f));
    }

    /**
     * @return The built class signature.
     */
    public ClassSignature getSignature() {
        return this.signature;
    }

}
