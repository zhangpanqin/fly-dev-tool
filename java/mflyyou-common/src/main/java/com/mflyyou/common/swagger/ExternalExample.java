package com.mflyyou.common.swagger;

import io.swagger.v3.oas.models.examples.Example;

public class ExternalExample extends BaseExample {
    private boolean forceReplace;

    public ExternalExample(String exampleName, Example example) {
        super(exampleName, example);
    }

    public boolean isForceReplace() {
        return forceReplace;
    }

    public void setForceReplace(boolean forceReplace) {
        this.forceReplace = forceReplace;
    }
}