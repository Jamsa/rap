package com.github.jamsa.rap.core.mbg;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;
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
        topLevelClass.addImportedType("com.github.jamsa.rap.core.model.BaseEntity");

        /*

        IntrospectedColumn keyCol = null;
        for(IntrospectedColumn column:introspectedTable.getAllColumns()){
           if(column.isIdentity()){
               keyCol = column;
               break;
           }
        }
        String keyType = "Object";
        if(keyCol!=null){
            keyType = keyCol.getFullyQualifiedJavaType().getShortName();
        }*/
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


        /*
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewMapInstance());
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewHashMapInstance());

        Field field = new Field("changedProperties",FullyQualifiedJavaType.getNewMapInstance());
        field.setInitializationString("new HashMap()");
        field.setVisibility(JavaVisibility.PROTECTED);
        topLevelClass.addField(field);
        Method method = new Method("getChangedProperties");
        method.setReturnType(FullyQualifiedJavaType.getNewMapInstance());
        method.addBodyLine("return this.changedProperties;");
        method.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addMethod(method);
        */

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
        FullyQualifiedJavaType keyType = new FullyQualifiedJavaType("Object");
        String keyName = "none";
        if(introspectedTable.hasPrimaryKeyColumns()){
            IntrospectedColumn column = introspectedTable.getPrimaryKeyColumns().get(0);
            keyType = column.getFullyQualifiedJavaType();
            keyName = column.getJavaProperty();
        }

        FullyQualifiedJavaType superType = new FullyQualifiedJavaType("com.github.jamsa.rap.core.mapper.BaseMapper");

        interfaze.addImportedType(superType);
        superType = new FullyQualifiedJavaType("BaseMapper");

        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        interfaze.addImportedType(modelType);


        superType.addTypeArgument(modelType);
        superType.addTypeArgument(keyType);

        interfaze.addSuperInterface(superType);

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

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
}
