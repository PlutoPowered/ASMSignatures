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
package com.gmail.socraticphoenix.asmsig.type;

import java.util.ArrayList;
import java.util.List;

import com.gmail.socraticphoenix.asmsig.Signatures;
import org.objectweb.asm.Type;

/**
 * Represents the type of a class declaration, with formal type parameters.
 */
public class TypeParameterized implements TypeSignaturePart {
    private Type type;
    private List<TypeVar> paras;

    /**
     * Creates a new parameterized type with the given {@link Type}.
     *
     * @param type The type.
     */
    public TypeParameterized(Type type) {
        this.type = type;
        this.paras = new ArrayList<>();
    }

    /**
     * @return The base type of this parameterized type.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Sets the base type of this parameterized type.
     *
     * @param type The new base type.
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return A list of the formal type parameters associated with this parameterized type.
     */
    public List<TypeVar> getParameters() {
        return this.paras;
    }

    /**
     * Adds a formal type parameter to this parameterized type.
     *
     * @param var The formal type parameter to add.
     */
    public void addParameter(TypeVar var) {
        this.paras.add(var);
    }

    /**
     * @return The formatted parameters of this parameterized type, as they would appear in bytecode.
     */
    public String writeParams() {
        StringBuilder k = new StringBuilder();
        if(!this.paras.isEmpty()) {
            k.append("<");
            this.paras.forEach(f -> k.append(f.write()));
            k.append(">");
        }
        return k.toString();
    }

    @Override
    public String write() {
        StringBuilder k = new StringBuilder().append(Signatures.writeStart(this.type));
        if(!this.paras.isEmpty()) {
            k.append("<");
            this.paras.forEach(f -> k.append(f.write()));
            k.append(">");
        }
        return k.append(Signatures.writeEnd(this.type)).toString();
    }

}