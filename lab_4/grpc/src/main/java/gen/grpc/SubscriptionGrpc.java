package gen.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: weatherSubscription.proto")
public final class SubscriptionGrpc {

  private SubscriptionGrpc() {}

  public static final String SERVICE_NAME = "src.main.proto.Subscription";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gen.grpc.LocationList,
      gen.grpc.Weather> getSubscribeForWeatherMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subscribeForWeather",
      requestType = gen.grpc.LocationList.class,
      responseType = gen.grpc.Weather.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<gen.grpc.LocationList,
      gen.grpc.Weather> getSubscribeForWeatherMethod() {
    io.grpc.MethodDescriptor<gen.grpc.LocationList, gen.grpc.Weather> getSubscribeForWeatherMethod;
    if ((getSubscribeForWeatherMethod = SubscriptionGrpc.getSubscribeForWeatherMethod) == null) {
      synchronized (SubscriptionGrpc.class) {
        if ((getSubscribeForWeatherMethod = SubscriptionGrpc.getSubscribeForWeatherMethod) == null) {
          SubscriptionGrpc.getSubscribeForWeatherMethod = getSubscribeForWeatherMethod = 
              io.grpc.MethodDescriptor.<gen.grpc.LocationList, gen.grpc.Weather>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "src.main.proto.Subscription", "subscribeForWeather"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gen.grpc.LocationList.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gen.grpc.Weather.getDefaultInstance()))
                  .setSchemaDescriptor(new SubscriptionMethodDescriptorSupplier("subscribeForWeather"))
                  .build();
          }
        }
     }
     return getSubscribeForWeatherMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SubscriptionStub newStub(io.grpc.Channel channel) {
    return new SubscriptionStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SubscriptionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SubscriptionBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SubscriptionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SubscriptionFutureStub(channel);
  }

  /**
   */
  public static abstract class SubscriptionImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribeForWeather(gen.grpc.LocationList request,
        io.grpc.stub.StreamObserver<gen.grpc.Weather> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeForWeatherMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubscribeForWeatherMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                gen.grpc.LocationList,
                gen.grpc.Weather>(
                  this, METHODID_SUBSCRIBE_FOR_WEATHER)))
          .build();
    }
  }

  /**
   */
  public static final class SubscriptionStub extends io.grpc.stub.AbstractStub<SubscriptionStub> {
    private SubscriptionStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SubscriptionStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SubscriptionStub(channel, callOptions);
    }

    /**
     */
    public void subscribeForWeather(gen.grpc.LocationList request,
        io.grpc.stub.StreamObserver<gen.grpc.Weather> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeForWeatherMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SubscriptionBlockingStub extends io.grpc.stub.AbstractStub<SubscriptionBlockingStub> {
    private SubscriptionBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SubscriptionBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SubscriptionBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<gen.grpc.Weather> subscribeForWeather(
        gen.grpc.LocationList request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeForWeatherMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SubscriptionFutureStub extends io.grpc.stub.AbstractStub<SubscriptionFutureStub> {
    private SubscriptionFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SubscriptionFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SubscriptionFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE_FOR_WEATHER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SubscriptionImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SubscriptionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE_FOR_WEATHER:
          serviceImpl.subscribeForWeather((gen.grpc.LocationList) request,
              (io.grpc.stub.StreamObserver<gen.grpc.Weather>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SubscriptionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SubscriptionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gen.grpc.WeatherSubscription.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Subscription");
    }
  }

  private static final class SubscriptionFileDescriptorSupplier
      extends SubscriptionBaseDescriptorSupplier {
    SubscriptionFileDescriptorSupplier() {}
  }

  private static final class SubscriptionMethodDescriptorSupplier
      extends SubscriptionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SubscriptionMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SubscriptionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SubscriptionFileDescriptorSupplier())
              .addMethod(getSubscribeForWeatherMethod())
              .build();
        }
      }
    }
    return result;
  }
}
