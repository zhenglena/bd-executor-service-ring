package com.amazon.ata.executorservice.test;

import com.amazon.ata.executorservice.coralgenerated.customer.GetCustomerDevicesRequest;
import com.amazon.ata.executorservice.coralgenerated.customer.GetCustomerDevicesResponse;
import com.amazon.ata.executorservice.coralgenerated.devicecommunication.GetDeviceSystemInfoRequest;
import com.amazon.ata.executorservice.checker.DeviceChecker;
import com.amazon.ata.executorservice.customer.CustomerService;
import com.amazon.ata.executorservice.devicecommunication.RingDeviceCommunicatorService;
import com.amazon.ata.executorservice.util.KnownRingDeviceFirmwareVersions;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.description;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Phase2Test {
    private CustomerService customerServiceSpy;
    private RingDeviceCommunicatorService deviceCommunicatorServiceSpy;
    private DeviceChecker deviceChecker;

    @BeforeEach
    private void setup() {
        customerServiceSpy = spy(CustomerService.getClient());
        deviceCommunicatorServiceSpy = spy(RingDeviceCommunicatorService.getClient());
        deviceChecker = new DeviceChecker(customerServiceSpy, deviceCommunicatorServiceSpy);
    }

    @Test
    void deviceChecker_checkDevicesIteratively_callsGetDeviceSystemInfoForDevice() {
        // GIVEN
        String customerId = "CUST1234";
        String deviceId = "1234";
        List<String> devicesList = ImmutableList.of(deviceId);
        // customer service spy to force reply including just the device(s) above
        GetCustomerDevicesRequest gcdRequest = GetCustomerDevicesRequest.builder().withCustomerId(customerId).build();
        GetCustomerDevicesResponse gcdResponse = GetCustomerDevicesResponse.builder()
                .withCustomerId(customerId)
                .withDeviceIds(devicesList)
                .build();
        when(customerServiceSpy.getCustomerDevices(gcdRequest)).thenReturn(gcdResponse);
        // RingDeviceCommunicatorService spy to verify call to GetDeviceSystemInfo operation

        // WHEN
        deviceChecker.checkDevicesIteratively(customerId, KnownRingDeviceFirmwareVersions.PINKY);

        // THEN
        // expected device system info request
        GetDeviceSystemInfoRequest gdsiRequest = GetDeviceSystemInfoRequest.builder().withDeviceId(deviceId).build();
        verify(
            deviceCommunicatorServiceSpy,
            description("Expected checkDevicesIteratively to result in GetDeviceSystemInfo call with device " + deviceId)
        ).getDeviceSystemInfo(gdsiRequest);
    }
}
