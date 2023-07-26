package io.github.silencelwy.generator.mongo;//package io.github.silencelwy.generator.mongo;
//
//import com.sun.source.tree.*;
//import com.sun.source.util.JavacTask;
//import com.sun.source.util.TreeScanner;
//import com.sun.tools.javac.api.JavacTool;
//import com.sun.tools.javac.file.JavacFileManager;
//import com.sun.tools.javac.util.Context;
//import com.sun.tools.javac.util.StringUtils;
//import lombok.SneakyThrows;
//import org.apache.commons.collections.CollectionUtils;
//
//import javax.tools.JavaCompiler;
//import javax.tools.JavaFileObject;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class JavaParser {
//
//    private JavacFileManager fileManager;
//    private JavacTool javacTool;
//
//    private Map<String, List<ClassField>> map;
//    private String clazzName;
//
//
//    public JavaParser() {
//        Context context = new Context();
//        fileManager = new JavacFileManager(context, true, Charset.defaultCharset());
//        javacTool = JavacTool.create();
//    }
//
//    @SneakyThrows
//    public String parseJavaFiles(String path) {
//        Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(path);
//        JavaCompiler.CompilationTask compilationTask = javacTool.getTask(null, fileManager, null, null, null, files);
//        JavacTask javacTask = (JavacTask) compilationTask;
//        try {
//            Iterable<? extends CompilationUnitTree> result = javacTask.parse();
//            for (CompilationUnitTree tree : result) {
//                SourceVisitor sourceVisitor = new SourceVisitor();
//                tree.accept(sourceVisitor, null);
//                map = sourceVisitor.getMap();
//                String sourceName =  tree.getSourceFile().getName();
//                int start = sourceName.lastIndexOf("/");
//                int end = sourceName.lastIndexOf(".");
//                this.clazzName = tree.getSourceFile().getName().substring(start+1,end);
//            }
//            for (Map.Entry<String,List<ClassField>> entry:map.entrySet()){
//                for (ClassField field : entry.getValue()){
//                    if (field.getType().contains("<") && field.getType().contains(">")){
//                        field.setArray(true);
//                        int start = field.getType().lastIndexOf("<");
//                        int end = field.getType().lastIndexOf(">");
//                        String type = field.getType().substring(start+1,end);
//                        field.setSubFields(map.get(type));
//                    }else {
//                        field.setSubFields(map.get(field.getType()));
//                    }
//                }
//            }
//
//            List<ClassField> classFields = map.get(this.clazzName);
//            return convertJson(classFields);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    private String convertJson(List<ClassField> classFields) {
//        StringBuilder sb = new StringBuilder();
//        for (ClassField field : classFields) {
//            sb.append(",");
//            sb.append("\"");
//            sb.append(field.getName());
//            sb.append("\"");
//            sb.append(":");
//            if (Boolean.TRUE.equals(field.getArray())) {
//                sb.append("[");
//            }
//            if (CollectionUtils.isNotEmpty(field.getSubFields())) {
//                sb.append(convertJson(field.getSubFields()));
//            } else {
//                sb.append(createVal(StringUtils.toUpperCase(field.getType())));
//            }
//            if (Boolean.TRUE.equals(field.getArray())) {
//                sb.append("]");
//            }
//        }
//        return "{" + sb.substring(1) + "}";
//    }
//
//    private String createVal(String type) {
//        String result;
//        switch (type) {
//            case "DOUBLE":
//            case "FLOAT":
//                result = "1.00";
//                break;
//            case "BOOLEAN":
//                result = "true";
//                break;
//            case "SHORT":
//            case "INTEGER":
//            case "LONG":
//                result = "1";
//                break;
//            default:
//                result = "\"测试数据\"";
//        }
//        return result;
//    }
//
//    public class SourceVisitor extends TreeScanner<Void, Void> {
//
//        private Map<String, List<ClassField>> map = new HashMap<>(8);
//
//        public Map<String, List<ClassField>> getMap(){
//            return this.map;
//        }
//
//        @Override
//        public Void visitClass(ClassTree node, Void aVoid) {
//            String name = node.getSimpleName().toString();
//            List<ClassField> list = new ArrayList<>();
//            for (Tree member : node.getMembers()) {
//                if (member instanceof VariableTree) {
//                    VariableTree variable = (VariableTree) member;
//                    list.add(new ClassField(variable.getName().toString(),variable.getType().toString()));
//                }
//            }
//            map.put(name,list);
//            return super.visitClass(node, aVoid);
//        }
//    }
//}
