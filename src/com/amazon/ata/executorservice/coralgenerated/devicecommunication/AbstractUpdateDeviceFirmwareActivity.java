package com.amazon.ata.executorservice.classroom.coralgenerated.devicecommunication;

import com.amazon.coral.service.Activity;
import com.amazon.coral.annotation.Operation;
import com.amazon.coral.annotation.Service;
import com.amazon.coral.annotation.Documentation;
import com.amazon.coral.annotation.Generated;

@Generated
@Service("RingDeviceCommunicatorService")
public abstract class AbstractUpdateDeviceFirmwareActivity extends Activity {

  @Operation("UpdateDeviceFirmware")
  @Documentation("Updates the specified device's firmware to the specified version.")
  public abstract UpdateDeviceFirmwareResponse enact(UpdateDeviceFirmwareRequest input);

}
