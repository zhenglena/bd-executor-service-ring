package com.amazon.ata.executorservice.classroom.coralgenerated.devicecommunication;

import com.amazon.coral.service.http.*;
import java.util.HashMap;

public class RingDeviceCommunicatorService$ExplorerContentFactory extends ServiceContentFactory {

  private final static HashMap<CharSequence, CharSequence> redirects = new HashMap<CharSequence, CharSequence>();
  static {
      redirects.put("/explorer", "/explorer/index.html");
  }

  public RingDeviceCommunicatorService$ExplorerContentFactory() {
    super(getDelegateFactory(), "RingDeviceCommunicatorService");
  }

  private static ContentFactory getDelegateFactory() {
    ContentFactory cf = new ClassLoaderContentFactory(RingDeviceCommunicatorService$ExplorerContentFactory.class.getClassLoader());
    cf = new PathMappedContentFactory(cf, "", "com/amazon/coral/model/generator/dashboard/ExplorerEmitter/RingDeviceCommunicatorService/");
    cf = new RestrictedContentFactory(cf,"style.css","jquery-3.4.1.min.js","jquery.autoresize.js","json2.js","coral.js","hmacsha1.js","signing.js","explore.js","2.0.0-crypto-sha256.js","2.0.0-hmac-min.js","2.0.0-crypto-min.js","model.js","model.json","index.html");
    cf = new PathMappedContentFactory(cf, "explorer/", "");
    cf = new RedirectContentFactory(cf, redirects);
    cf = new PrefixRestrictedContentFactory(cf, "explorer/");
    cf = new CachedContentFactory(cf, 14);
    return cf;
  }
}
