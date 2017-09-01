package com.pablisco.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static com.pablisco.reflect.ReflectiveType.reflect;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ReflectiveFieldTest {

    @Parameter(0)
    public Class<? extends Subject> type;

    @Parameters(name = "{0}")
    public static Collection parameters() {
        return Arrays.asList(new Object[][] {
                {MainSubject.class},
                {ChildSubject.class}
        });
    }

    @Test
    public void shouldReadNonFinalMemberField() throws Exception {
        String actual = reflect(type.newInstance()).field("nonFinalMemberField").getAs(String.class);
        assertThat(actual).isEqualTo("nonFinalMemberField");
    }

    @Test
    public void shouldReadFinalMemberField() throws Exception {
        String actual = reflect(type.newInstance()).field("finalMemberField").getAs(String.class);
        assertThat(actual).isEqualTo("finalMemberField");
    }

    @Test
    public void shouldReadNonFinalStaticField() throws Exception {
        String actual = reflect(type).field("nonFinalStaticField").getAs(String.class);
        assertThat(actual).isEqualTo("nonFinalStaticField");
    }

    @Test
    public void shouldReadFinalStaticField() throws Exception {
        String actual = reflect(type).field("finalStaticField").getAs(String.class);
        assertThat(actual).isEqualTo("finalStaticField");
    }

    @Test
    public void shouldWriteNonFinalMemberField() throws Exception {
        Subject subject = type.newInstance();
        reflect(subject).field("nonFinalMemberField").set("passed");
        assertThat(subject.getNonFinalMemberField()).isEqualTo("passed");
    }

    @Test
    public void shouldWriteFinalMemberField() throws Exception {
        Subject subject = type.newInstance();
        reflect(subject).field("finalMemberField").set("passed");
        assertThat(subject.getFinalMemberField()).isEqualTo("finalMemberField");
    }

    @Test
    public void shouldWriteNonFinalStaticField() throws Exception {
        reflect(type).field("nonFinalStaticField").set("passed");
        assertThat(MainSubject.nonFinalStaticField).isEqualTo("passed");
        // TODO make reset
        reflect(type).field("nonFinalStaticField").set("nonFinalStaticField");
    }

    @Test
    public void shouldWriteFinalStaticField() throws Exception {
        reflect(type).field("finalStaticField").set("passed");
        assertThat(MainSubject.finalStaticField).isEqualTo("passed");
        // TODO make reset
        reflect(type).field("finalStaticField").set("finalStaticField");
    }

    public static class MainSubject implements Subject {

        @SuppressWarnings("RedundantStringToString")
        private static final String finalStaticField = "finalStaticField".toString();
        private static String nonFinalStaticField = "nonFinalStaticField";
        private final String finalMemberField = "finalMemberField";
        private String nonFinalMemberField = "nonFinalMemberField";

        public static String getFinalStaticField() {
            return finalStaticField;
        }

        public static String getNonFinalStaticField() {
            return nonFinalStaticField;
        }

        @Override
        public String getFinalMemberField() {
            return finalMemberField;
        }

        @Override
        public String getNonFinalMemberField() {
            return nonFinalMemberField;
        }
    }

    public static class ChildSubject extends MainSubject {

    }

    public interface Subject {
        String getFinalMemberField();

        String getNonFinalMemberField();
    }

}