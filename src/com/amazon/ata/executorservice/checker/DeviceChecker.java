package com.amazon.ata.executorservice.checker;

import com.amazon.ata.executorservice.coralgenerated.customer.GetCustomerDevicesRequest;
import com.amazon.ata.executorservice.coralgenerated.customer.GetCustomerDevicesResponse;
import com.amazon.ata.executorservice.coralgenerated.devicecommunication.RingDeviceFirmwareVersion;
import com.amazon.ata.executorservice.coralgenerated.devicecommunication.UpdateDeviceFirmwareRequest;
import com.amazon.ata.executorservice.coralgenerated.devicecommunication.UpdateDeviceFirmwareResponse;
import com.amazon.ata.executorservice.customer.CustomerService;
import com.amazon.ata.executorservice.devicecommunication.RingDeviceCommunicatorService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility object for checking version status of devices, and updating
 * them if necessary.
 *
 * For instructional purposes, two implementations of the same logic
 * will be created: checkDevicesIteratively, and checkDevicesConcurrently.
 */
public class DeviceChecker {
    private final CustomerService customerService;
    private final RingDeviceCommunicatorService ringDeviceCommunicatorService;

    /**
     * Constructs a DeviceChecker with the provided dependencies.
     *
     * PARTICIPANTS: Do not change the signature of this constructor
     * @param customerService The CustomerService client to use for Customer operations
     * @param ringDeviceCommunicatorService The RingDeviceCommunicatorService client to use for
     *                                      device communication operations
     */
    public DeviceChecker(CustomerService customerService, RingDeviceCommunicatorService ringDeviceCommunicatorService) {
        this.customerService = customerService;
        this.ringDeviceCommunicatorService = ringDeviceCommunicatorService;
    }

    /**
     * Iteratively checks all devices for the given customer.
     * @param customerId The customer to check devices for
     * @param version The firmware version that we want all devices updated to
     * @return The number of devices that were checked
     */
    public int checkDevicesIteratively(final String customerId, RingDeviceFirmwareVersion version) {
        List<String> deviceIds = getDeviceIds(customerId);
        for (String deviceId : deviceIds) {
            DeviceCheckTask checkTask = new DeviceCheckTask(this, deviceId, version);
            checkTask.run();
        }

        return deviceIds.size();

    }

    /**
     * Concurrently checks all devices for the given customer.
     * @param customerId The customer to check devices for
     * @param version The firmware version that we want all devices updated to
     * @return The number of devices that were checked
     */
    public int checkDevicesConcurrently(final String customerId, RingDeviceFirmwareVersion version) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<String> deviceIds = getDeviceIds(customerId);
        deviceIds.forEach(id -> {
            DeviceCheckTask checkTask = new DeviceCheckTask(this, id, version);
            executor.submit(checkTask);
        });
        executor.shutdown();
        return deviceIds.size();
    }

    /**
     * Updates the device to the specified version.
     * @param deviceId The device identifier of the device to update
     * @param version The version the device should be updated to
     */
    public void updateDevice(final String deviceId, final RingDeviceFirmwareVersion version) {
        System.out.println(String.format("[DeviceChecker] Updating device %s to version %s", deviceId, version));
        UpdateDeviceFirmwareRequest  request = UpdateDeviceFirmwareRequest.builder()
                .withDeviceId(deviceId)
                .withVersion(version)
                .build();
        ringDeviceCommunicatorService.updateDeviceFirmware(request);
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public RingDeviceCommunicatorService getRingDeviceCommunicatorService() {
        return ringDeviceCommunicatorService;
    }

    private List<String> getDeviceIds(String customerId) {
        GetCustomerDevicesRequest request = GetCustomerDevicesRequest.builder()
                .withCustomerId(customerId)
                .build();
        GetCustomerDevicesResponse response = customerService.getCustomerDevices(request);
        return response.getDeviceIds();
    }
}
