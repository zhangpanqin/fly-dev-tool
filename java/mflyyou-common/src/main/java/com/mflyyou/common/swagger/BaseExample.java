package com.mflyyou.common.swagger;

import io.swagger.v3.oas.models.examples.Example;

import java.util.Objects;

public abstract class BaseExample {
    protected final String exampleName;
    protected final Example example;

    public BaseExample(String exampleName, Example example) {
        this.exampleName = Objects.requireNonNull(exampleName);
        this.example = Objects.requireNonNull(example);
    }

    public String getExampleName() {
        return exampleName;
    }

    public Example getExample() {
        return example;
    }
}
