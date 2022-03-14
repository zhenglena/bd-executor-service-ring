package com.amazon.ata.executorservice.classroom.coralgenerated.devicecommunication;

import com.amazon.coral.annotation.Shape;
import com.amazon.coral.annotation.Wrapper;
import com.amazon.coral.annotation.XmlName;
import com.amazon.coral.annotation.XmlNamespace;
import com.amazon.coral.annotation.*;
import java.util.Arrays;
import java.util.Objects;

@Shape
  @XmlName(value="GetDeviceSystemInfoResponse")
  @XmlNamespace(value="http://internal.amazon.com/coral/com.amazon.ata.executorservice.classroom.devicecommunication/")
  @Wrapper(value={com.amazon.coral.annotation.WrapperType.INPUT,com.amazon.coral.annotation.WrapperType.OUTPUT})
@com.amazon.coral.annotation.Generated
@com.amazon.coral.annotation.CoralSuppressWarnings
public class GetDeviceSystemInfoResponse extends Object  {

  /**
   * Statically creates a builder instance for GetDeviceSystemInfoResponse.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Fluent builder for instances of GetDeviceSystemInfoResponse.
   */
  @com.amazon.coral.annotation.Generated
  public static class Builder {

    protected RingDeviceSystemInfo systemInfo;
    /**
     * Sets the value of the field "systemInfo" to be used for the constructed object.
     * @param systemInfo
     *   The value of the "systemInfo" field.
     * @return
     *   This builder.
     */
    public Builder withSystemInfo(RingDeviceSystemInfo systemInfo) {
      this.systemInfo = systemInfo;
      return this;
    }

    /**
     * Sets the fields of the given instances to the corresponding values recorded when calling the "with*" methods.
     * @param instance
     *   The instance to be populated.
     */
    protected void populate(GetDeviceSystemInfoResponse instance) {
      instance.setSystemInfo(this.systemInfo);
    }

    /**
     * Builds an instance of GetDeviceSystemInfoResponse.
     * <p>
     * The built object has its fields set to the values given when calling the "with*" methods of this builder.
     * </p>
     */
    public GetDeviceSystemInfoResponse build() {
      GetDeviceSystemInfoResponse instance = new GetDeviceSystemInfoResponse();

      populate(instance);

      return instance;
    }
  };

  private RingDeviceSystemInfo systemInfo;

  public RingDeviceSystemInfo getSystemInfo() {
    return this.systemInfo;
  }

  public void setSystemInfo(RingDeviceSystemInfo systemInfo) {
    this.systemInfo = systemInfo;
  }

  private static final int classNameHashCode =
      internalHashCodeCompute("com.amazon.ata.executorservice.classroom.devicecommunication.GetDeviceSystemInfoResponse");

  /**
   * HashCode implementation for GetDeviceSystemInfoResponse
   * based on java.util.Arrays.hashCode
   */
  @Override
  public int hashCode() {
    return internalHashCodeCompute(
        classNameHashCode,
        getSystemInfo());
  }

  private static int internalHashCodeCompute(Object... objects) {
    return Arrays.hashCode(objects);
  }

  /**
   * Equals implementation for GetDeviceSystemInfoResponse
   * based on instanceof and Object.equals().
   */
  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof GetDeviceSystemInfoResponse)) {
      return false;
    }

    GetDeviceSystemInfoResponse that = (GetDeviceSystemInfoResponse) other;

    return
        Objects.equals(getSystemInfo(), that.getSystemInfo());
  }

  /**
   * Returns a string representation of this object. The content of any types marked with the
   * <a href="https://w.amazon.com/index.php/Coral/Model/XML/Traits#Sensitive">sensitive</a>
   * trait will be redacted.
   * <p/>
   * <b>Do not attempt to parse the string returned by this method.</b> Coral's <tt>toString</tt>
   * format is undefined and subject to change. To obtain a proper machine-readable representation
   * of this object, use Coral Serialize directly.
   * @see <a href="https://w.amazon.com/index.php/Coral/Serialize/Manual">Coral Serialize Manual</a>
   * @see <a href="https://w.amazon.com/index.php/Coral/Serialize/FAQ">Coral Serialize FAQ</a>
   */
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    ret.append("GetDeviceSystemInfoResponse(");

    ret.append("systemInfo=");
    ret.append(String.valueOf(systemInfo));
    ret.append(")");

    return ret.toString();
  }

}
