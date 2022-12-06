package ru.levelup.at.homework2;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("ru.levelup.at.homework2")
@IncludeTags({"Negative", "Positive"})
public class AllTestsSuite {
    //postive tests
}
