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

import com.gmail.socraticphoenix.asmsig.Signatures;
import com.gmail.socraticphoenix.asmsig.signature.MethodSignature;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Test<VAR extends Throwable> {

    public <LV, P extends Number> List<VAR> funny(P k, int a, VAR b, Collection<? extends VAR> coll, Map<String, VAR> map) throws VAR, IOException {
        return null;
    }

    List<?> list;

    public static void main(String[] args) {
        MethodSignature signature = Signatures.parseMethod("<LV:Ljava/lang/Object;P:Ljava/lang/Number;>(TP;ITVAR;Ljava/util/Collection<+TVAR;>;Ljava/util/Map<Ljava/lang/String;TVAR;>;)Ljava/util/List<TVAR;>;^TVAR;^Ljava/io/IOException;");
        System.out.println("<LV:Ljava/lang/Object;P:Ljava/lang/Number;>(TP;ITVAR;Ljava/util/Collection<+TVAR;>;Ljava/util/Map<Ljava/lang/String;TVAR;>;)Ljava/util/List<TVAR;>;^TVAR;^Ljava/io/IOException;");
        System.out.println(signature.write());
    }

}
