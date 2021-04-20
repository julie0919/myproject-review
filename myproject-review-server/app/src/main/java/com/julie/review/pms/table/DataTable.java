package com.julie.review.pms.table;

import com.julie.review.util.Request;
import com.julie.review.util.Response;

public interface DataTable {
  void service(Request request, Response response) throws Exception;
}
