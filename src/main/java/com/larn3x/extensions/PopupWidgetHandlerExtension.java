package com.larn3x.extensions;

import com.larn3x.annotations.apps.VkVideo;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.larn3x.pages.Pages.vkVideoMainPage;

public class PopupWidgetHandlerExtension implements BeforeTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        if (context.getRequiredTestClass().isAnnotationPresent(VkVideo.class)) {
            vkVideoMainPage.handleSignInWidgetIfPresent();
        }
    }
}