package com.ms.back.global.exception;

import static com.ms.back.global.exception.ErrorCode.*;

public class MediscanCustomException extends MediscanException{
    public MediscanCustomException(final ErrorCode e) {
        super(e);
    }

    public static class NotFoundFileException extends MediscanCustomException {
        public NotFoundFileException() {
            super(NOT_FOUND_FILE);
        }
    }

    public static class FileReadingErrorException extends MediscanCustomException {
        public FileReadingErrorException() {
            super(FILE_READING_ERROR);
        }
    }

    public static class InvalidFileFormatException extends MediscanCustomException {
        public InvalidFileFormatException() {
            super(INVALID_FILE_FORMAT);
        }
    }
}
