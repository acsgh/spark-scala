package acsgh.spark.scala.exception;

public class UnexpectedContentTypeException extends IllegalArgumentException{
    public UnexpectedContentTypeException(String contentType) {
        super("Unexpected content type '" + contentType + "'");
    }
}
