package aye.model;

import java.util.List;

/**
 * Created by reid on 2016/12/10.
 */

public class HttpResponse<T> {
    public String reason;
    public int error_code;
    public List<T> result;
}
