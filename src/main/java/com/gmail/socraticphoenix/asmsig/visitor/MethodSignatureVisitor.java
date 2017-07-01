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

import com.gmail.socraticphoenix.asmsig.signature.MethodSignature;
import com.gmail.socraticphoenix.asmsig.type.TypeFill;
import com.gmail.socraticphoenix.asmsig.type.TypeVar;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;

/**
 * A {@link SignatureVisitor} which builds a {@link MethodSignature}.
 */
public class MethodSignatureVisitor extends SignatureVisitor implements Opcodes {
    private MethodSignature signature;
    private TypeVar previous;

    /**
     * Creates a new MethodSignatureVisitor
     */
    public MethodSignatureVisitor() {
        super(ASM5);
        this.signature = new MethodSignature(new TypeFill(Type.getType(void.class)));
    }

    @Override
    public void visitFormalTypeParameter(String name) {
        this.previous = new TypeVar(name);
        this.signature.addGeneric(this.previous);
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
    public SignatureVisitor visitParameterType() {
        return new TypeSignatureVisitor(f -> this.signature.addParameter(f));
    }

    @Override
    public SignatureVisitor visitReturnType() {
        return new TypeSignatureVisitor(f -> this.signature.setReturn(f));
    }

    @Override
    public SignatureVisitor visitExceptionType() {
        return new TypeSignatureVisitor(f -> this.signature.addException(f));
    }

    /**
     * @return The built method signature.
     */
    public MethodSignature getSignature() {
        return this.signature;
    }

}
