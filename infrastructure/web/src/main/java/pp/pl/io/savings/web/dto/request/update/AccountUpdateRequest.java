package pp.pl.io.savings.web.dto.request.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "accountType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SavingsAccountUpdateRequest.class, name = "SAVINGS")
})
public interface AccountUpdateRequest {

  String getAccountId();

  String getName();

  String getDescription();

  String getAccountType();
}
