package com.larn3x.pages;

import com.larn3x.pages.alchemypuzzle.AlchemyHintSlideBar;
import com.larn3x.pages.alchemypuzzle.AlchemyMainPage;
import com.larn3x.pages.alchemypuzzle.AlchemyPuzzlePage;
import com.larn3x.pages.vkvideo.VkVideoMainPage;
import com.larn3x.pages.vkvideo.VkVideoPlayerPage;

public final class Pages {

    public static final AlchemyMainPage alchemyMainPage = new AlchemyMainPage();
    public static final AlchemyPuzzlePage alchemyPuzzlePage = new AlchemyPuzzlePage();
    public static final AlchemyHintSlideBar alchemyHintSlideBar = new AlchemyHintSlideBar();
    public static final VkVideoMainPage vkVideoMainPage = new VkVideoMainPage();
    public static final VkVideoPlayerPage vkVideoPlayerPage = new VkVideoPlayerPage();

    private Pages(){}
}