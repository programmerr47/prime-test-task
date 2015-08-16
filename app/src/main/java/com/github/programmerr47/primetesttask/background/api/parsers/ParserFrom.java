package com.github.programmerr47.primetesttask.background.api.parsers;

/**
 * @author Michael Spitsin
 * @since 2015-08-08
 */
public interface ParserFrom<ParseResult, FromObject> {
    ParseResult parse(FromObject object);
}
