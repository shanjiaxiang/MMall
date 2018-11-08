package com.latte.compiler;

import com.google.auto.service.AutoService;
import com.latte.annotations.AppRegisterGenerator;
import com.latte.annotations.EntryGenerator;
import com.latte.annotations.PayEntryGenerator;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Administrator on 2018\11\8 0008.
 */

@AutoService(Processor.class)
public class LatteProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        return false;
    }

    @Override
    public Set <String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet <>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation : supportAnnotations){
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations(){
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet <>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;

    }




}
