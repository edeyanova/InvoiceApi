import com.example.invoiceapi.exceptions.InvalidInputException
import com.example.invoiceapi.exceptions.InvoiceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvoiceNotFoundException)
    static ResponseEntity<String> handleInvoiceNotFoundException(InvoiceNotFoundException e) {
        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found with id: " + e.getId())
    }

    @ExceptionHandler(InvalidInputException)
    static ResponseEntity<String> handleInvalidInputException(InvalidInputException e) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @ExceptionHandler(Exception)
    static ResponseEntity<String> handleGenericException(Exception e) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.message)
    }
}
