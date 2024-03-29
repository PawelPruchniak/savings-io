package pp.pl.io.savings.web.dto.request.create;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "accountType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SavingsAccountRequest.class, name = "SAVINGS")
})
public interface AccountRequest {

  String getName();

  String getDescription();

  String getAccountType();
}
