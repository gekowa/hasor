/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.dataql.compiler.ast.inst;
import net.hasor.dataql.Option;
import net.hasor.dataql.compiler.FormatWriter;
import net.hasor.dataql.compiler.ast.Inst;
import net.hasor.dataql.compiler.ast.Variable;
import net.hasor.dataql.compiler.qil.CompilerStack;
import net.hasor.dataql.compiler.qil.InstQueue;
import net.hasor.utils.StringUtils;

import java.io.IOException;

/**
 * return指令
 * @author 赵永春 (zyc@hasor.net)
 * @version : 2019-11-07
 */
public class ReturnInst extends Inst {
    private int      returnCode;
    private Variable resultData;

    public ReturnInst(int returnCode, Variable resultData) {
        this.returnCode = returnCode;
        this.resultData = resultData;
    }

    @Override
    public void doFormat(int depth, Option formatOption, FormatWriter writer) throws IOException {
        String fixedString = StringUtils.fixedString(' ', depth * fixedLength);
        //
        if (this.returnCode != 0) {
            writer.write(fixedString + String.format("return %s, ", this.returnCode));
        } else {
            writer.write(fixedString + "return ");
        }
        this.resultData.doFormat(depth + 1, formatOption, writer);
        writer.write("\n");
    }

    @Override
    public void doCompiler(InstQueue queue, CompilerStack stackTree) {
        this.resultData.doCompiler(queue, stackTree);
        queue.inst(END);
    }
}