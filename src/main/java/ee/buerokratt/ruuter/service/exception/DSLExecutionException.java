package ee.buerokratt.ruuter.service.exception;

import ee.buerokratt.ruuter.helper.exception.ScriptEvaluationException;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class DSLExecutionException extends Exception {

    public static final String ERR_UNKNOWN = "E_unknown";
    public static final String ERR_VALUE_NULL = "E_null";
    private static final String ERR_VALUE_SCRIPTING = "E_script";

    String dslName;
    String stepName;
    String causeCode;

    public DSLExecutionException(String stepName, String dslName, Throwable err) {
        this.stepName = stepName;
        this.dslName = dslName;

        if (err instanceof NullPointerException) {
            this.causeCode = ERR_VALUE_NULL;
        } if (err instanceof ScriptEvaluationException){
            this.causeCode = ERR_VALUE_SCRIPTING;
            log.error("scripting error: " + ((ScriptEvaluationException) err).getMessage());
        } else
            this.causeCode = ERR_UNKNOWN;
    }

    public ErrorObject getErrorObject() {
        return new ErrorObject(this.dslName, this.stepName, this.causeCode);
    }

    @Value
    public static class ErrorObject {
        String dslName;
        String stepName;
        String causeCode;
    }
}
