package com.bartek.groupchat.user.app.controllers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class EntryControllerTest {
    private static Stream<Arguments> provideTestArgs(){
        return Stream.of(
            Arguments.of("nazwa22", true),
                Arguments.of("Janek123", true),
                Arguments.of("Franek_2009", true),
                Arguments.of("", false),
                Arguments.of("'DROP DATABASE; --", false),
                Arguments.of("       ", false),
                Arguments.of("Fr4nek{}", false)
        );
    }
    EntryController entryController = spy(EntryController.class);
    @ParameterizedTest
    @MethodSource("provideTestArgs")
    void testValidateUsername(String input, boolean expected){
        assertEquals(expected, entryController.validateUsername(input));
    }

}