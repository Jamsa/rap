package com.github.jamsa.rap.core.mbg;

import java.sql.Types;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * Created by zhujie on 16/8/22.
 */
public class MyBatisGeneratorPlugin extends PluginAdapter {
    private Log logger = LogFactory.getLog(MyBatisGeneratorPlugin.class);

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        //添加父类导入,设置父类及其泛型参数
        topLevelClass.addImportedType("com.github.jamsa.rap.core.model.BaseEntity");
        FullyQualifiedJavaType keyType = new FullyQualifiedJavaType("Object");
        String keyName = "none";
        if(introspectedTable.hasPrimaryKeyColumns()){
            IntrospectedColumn column = introspectedTable.getPrimaryKeyColumns().get(0);
            keyType = column.getFullyQualifiedJavaType();
            keyName = column.getJavaProperty();
        }
        FullyQualifiedJavaType superType = new FullyQualifiedJavaType("com.github.jamsa.rap.core.model.BaseEntity");
        superType.addTypeArgument(keyType);
        topLevelClass.setSuperClass(superType);

        //添加接口方法的实现
        Method method = new Method("getPrimaryKey");
        method.setReturnType(keyType);
        method.addBodyLine("return this."+keyName+";");
        method.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addMethod(method);

        method = new Method("setPrimaryKey");
        method.addParameter(new Parameter(keyType,"pkValue"));

        String setterName = (keyName.charAt(0)+"").toUpperCase()+keyName.substring(1);
        method.addBodyLine("this.set"+setterName+"(pkValue);");

        method.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addMethod(method);

        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        method.addBodyLine(0,"this.firePropertyChange(\""+method.getParameters().get(0).getName()+"\","+method.getParameters().get(0).getName()+");");
        return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //获取主键类型
        FullyQualifiedJavaType keyType = new FullyQualifiedJavaType("Object");
        String keyName = "none";
        if(introspectedTable.hasPrimaryKeyColumns()){
            IntrospectedColumn column = introspectedTable.getPrimaryKeyColumns().get(0);
            keyType = column.getFullyQualifiedJavaType();
            keyName = column.getJavaProperty();
        }

        //添加父类及其泛型参数
        FullyQualifiedJavaType superType = new FullyQualifiedJavaType("com.github.jamsa.rap.core.mapper.BaseMapper");

        interfaze.addImportedType(superType);
        superType = new FullyQualifiedJavaType("BaseMapper");

        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        interfaze.addImportedType(modelType);


        superType.addTypeArgument(modelType);
        superType.addTypeArgument(keyType);

        interfaze.addSuperInterface(superType);

        //生成selectByCondition方法
        Method selectMethod = new Method("selectByCondition");
        selectMethod.addParameter(new Parameter(modelType,"condition"));
        FullyQualifiedJavaType selectReturn = FullyQualifiedJavaType.getNewListInstance();
        interfaze.addImportedType(selectReturn);
        selectReturn = new FullyQualifiedJavaType("List");
        selectReturn.addTypeArgument(modelType);
        selectMethod.setReturnType(selectReturn);
        selectMethod.addJavaDocLine("/** @mbggenerated */");
        interfaze.addMethod(selectMethod);


        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    /**
     * 修改test条件
     * @param element
     * @param ifEleParentName
     */
    private void modifyIfCondition(XmlElement element, String ifEleParentName){
        for(Element trimEle : element.getElements()){
            if(trimEle instanceof XmlElement && ((XmlElement) trimEle).getName().toLowerCase().equals(ifEleParentName)){
                for(Element ifEle: ((XmlElement) trimEle).getElements()){
                    if(ifEle instanceof XmlElement && ((XmlElement) ifEle).getName().toLowerCase().equals("if")){
                        for(Attribute attr : ((XmlElement) ifEle).getAttributes()){
                            if(attr.getName().toLowerCase().equals("test")){
                                String value = attr.getValue();
                                int idx = value.indexOf(" != null");

                                if(idx>0){
                                    value = "changedProperties.containsKey('"+value.substring(0,idx)+"')";
                                    ((XmlElement) ifEle).getAttributes().remove(attr);
                                    ((XmlElement) ifEle).addAttribute(new Attribute("test",value));
                                    //logger.error(value);
                                    //return super.sqlMapInsertSelectiveElementGenerated(element,introspectedTable);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.modifyIfCondition(element,"trim");
        return super.sqlMapInsertSelectiveElementGenerated(element,introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.modifyIfCondition(element,"set");
        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        //添加生成selectByCondition节点
        XmlElement element = new XmlElement("select");
        document.getRootElement().addElement(element);
        element.addElement(new TextElement("<!--\n" +
                "      WARNING - @mbggenerated\n" +
                "    -->"));

        element.addAttribute(new Attribute("id","selectByCondition"));
        element.addAttribute(new Attribute("parameterType",introspectedTable.getBaseRecordType()));
        element.addAttribute(new Attribute("resultMap","BaseResultMap"));
        element.addElement(new TextElement("select"));

        XmlElement includeEle = new XmlElement("include");
        includeEle.addAttribute(new Attribute("refid","Base_Column_List"));
        element.addElement(includeEle);
        element.addElement(new TextElement("from " + introspectedTable.getTableConfiguration().getTableName()));

        XmlElement trimEle = new XmlElement("trim");
        element.addElement(trimEle);
        trimEle.addAttribute(new Attribute("prefix"," where "));
        trimEle.addAttribute(new Attribute("suffixOverrides","and"));
        for (IntrospectedColumn column : introspectedTable.getAllColumns()){
            XmlElement ifEle = new XmlElement("if");
            trimEle.addElement(ifEle);
            ifEle.addAttribute(new Attribute("test","changedProperties.containsKey('"+column.getJavaProperty()+"')"));
            String content = "";
            if(column.getJdbcType() == Types.VARCHAR || column.getJdbcType() == Types.NVARCHAR){
                content  = column.getActualColumnName() + " like concat(concat('%',#{"+column.getJavaProperty()+", jdbcType="+column.getJdbcTypeName()+"}),'%') and ";
            }else{
                content = column.getActualColumnName() + " = #{"+column.getJavaProperty()+", jdbcType="+column.getJdbcTypeName()+"} and";
            }
            ifEle.addElement(new TextElement(content));
        }

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }


}
