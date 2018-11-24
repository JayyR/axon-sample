package io.jay.entity.value;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
public class FindAccountQuery {

    UUID accountId;
}
