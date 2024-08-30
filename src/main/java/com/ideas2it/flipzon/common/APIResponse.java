package com.ideas2it.flipzon.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <p>
 * API response class represents API response details.
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class APIResponse {
    private Integer status;
    private Object data;
    private Object error;
}
