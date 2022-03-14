package com.amazon.ata.executorservice.classroom.coralgenerated.customer;

import com.amazon.coral.service.Activity;
import com.amazon.coral.annotation.Operation;
import com.amazon.coral.annotation.Service;
import com.amazon.coral.annotation.Documentation;
import com.amazon.coral.annotation.Generated;

@Generated
@Service("CustomerService")
public abstract class AbstractGetCustomerDevicesActivity extends Activity {

  @Operation("GetCustomerDevices")
  @Documentation("Gets the IDs of Ring devices owned by a particular customer.")
  public abstract GetCustomerDevicesResponse enact(GetCustomerDevicesRequest input);

}
