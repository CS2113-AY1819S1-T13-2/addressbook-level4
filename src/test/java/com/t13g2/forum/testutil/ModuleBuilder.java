package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.Module;
//@@author xllx1
/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_MODULE_TITLE = "Software Engineering and OOP";

    private String moduleCode;
    private String moduleTitle;

    public ModuleBuilder() {
        this.moduleCode = DEFAULT_MODULE_CODE;
        this.moduleTitle = DEFAULT_MODULE_TITLE;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        this.moduleCode = moduleToCopy.getModuleCode();
        this.moduleTitle = moduleToCopy.getTitle();
    }

    /**
     * Sets the {@code mName} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String mCode) {
        this.moduleCode = mCode;
        return this;
    }

    /**
     * Sets the {@code mTitle} of the {@code Module} that we are building.
     */
    public ModuleBuilder withTitle(String mTitle) {
        this.moduleTitle = mTitle;
        return this;
    }

    public Module build() {
        return new Module(moduleTitle, moduleCode);
    }
}
