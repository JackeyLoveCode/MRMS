package com.hiveview.mrms.util;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * Created by chimeilong on 2017/6/29.
 */
public class MyTagRuleBundle implements TagRuleBundle {
    @Override
    public void install(State defaultState, ContentProperty contentProperty,
                        SiteMeshContext siteMeshContext) {
        //增加自定义tag
        defaultState.addRule("myScript", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("myScript"), false));

    }

    @Override
    public void cleanUp(State defaultState, ContentProperty contentProperty,
                        SiteMeshContext siteMeshContext) {
    }
}
