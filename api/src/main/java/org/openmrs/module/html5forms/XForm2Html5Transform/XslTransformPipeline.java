package org.openmrs.module.html5forms.xForm2Html5Transform;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class XslTransformPipeline {
    private Queue<File> transforms;


    private XslTransformPipeline() {
        transforms = new LinkedList<File>();

    }

    public static XslTransformPipeline xslTransformPipeline() {
        return new XslTransformPipeline();
    }


    public XslTransformPipeline push(File transform) {
        transforms.add(transform);
        return this;
    }

    public Stack<File> get() {
        Queue<File> clonedTransforms = new LinkedList<File>();
        Stack<File> pipeline = new Stack<File>();
        while(!transforms.isEmpty()){
            File transform = transforms.poll();
            clonedTransforms.add(transform);
            pipeline.add(transform);
        }
        transforms = clonedTransforms;
        return pipeline;
    }


    public static XslTransformPipeline xform2HTML5Pipeline() throws IOException {
        XslTransformPipeline pipeline = new XslTransformPipeline();
        pipeline.push(getXslFile("/xform2jr.xsl"))
                .push(getXslFile("jr2html5_php5.xsl"));
        return pipeline;
    }

    private static File getXslFile(String fileName) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext();
        return context.getResource(fileName).getFile();
    }
}