
package com.cydeo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefaultExceptionMessageDto {

    private String message;
}
// by this class , we'll be able to show our exception messages on ui.
