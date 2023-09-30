package net.buscacio.travelApi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ValidationException extends MessageException {

    private List<FieldMessage> messages = new ArrayList<>();
    public ValidationException(Integer status, String msg) {
        super( msg);
    }


    public void addError(String fieldName, String message) {
        messages.add(new FieldMessage(fieldName, message));
    }
}
