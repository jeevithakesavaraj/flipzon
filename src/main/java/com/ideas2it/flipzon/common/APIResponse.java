package com.ideas2it.flipzon.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class APIResponse {
    private Integer status;
    private Object data;
    private Object error;
}
