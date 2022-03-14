package com.amazon.ata.executorservice.classroom.coralgenerated.devicecommunication;

import com.amazon.coral.service.Activity;
import com.amazon.coral.annotation.Operation;
import com.amazon.coral.annotation.Service;
import com.amazon.coral.annotation.Documentation;
import com.amazon.coral.annotation.Generated;

@Generated
@Service("RingDeviceCommunicatorService")
public abstract class AbstractGetDeviceSystemInfoActivity extends Activity {

  @Operation("GetDeviceSystemInfo")
  @Documentation("Gets the system info of the specified Ring device.")
  public abstract GetDeviceSystemInfoResponse enact(GetDeviceSystemInfoRequest input);

}
