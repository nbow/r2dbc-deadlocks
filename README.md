# r2dbc-deadlocks

Example app with deadlock issues

Select Java version when using jabbab
```$xslt
jabba use
```
Build the docker image
```$xslt
docker build -t deadlocks .
```
Start docker container
```$xslt
docker run -p 1433:1433 deadlocks
```
Start the app
```$xslt
./gradlew bootRun
```

While the application is running an external actor causes a deadlock and the below error is encountered.

Example of error encountered
```$xslt
{"timestamp":"2020-02-25 18:00:16.706",
"level":"ERROR","thread":"reactor-tcp-nio-5",
"logger":"reactor.core.publisher.Operators",
"message":"Operator called default onErrorDropped",
"context":"default",
"exception":"io.r2dbc.mssql.ExceptionFactory$MssqlRollbackException: Transaction (Process ID 80) was deadlocked on lock resources with another process and has been chosen as the deadlock victim. Rerun the transaction.\r\n\tat io.r2dbc.mssql.ExceptionFactory.createException(ExceptionFactory.java:148)\r\n\tSuppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: \nError has been observed at the following site(s):\n\t|_ checkpoint ? SQL \"...SQL STATEMENT REDACTED..." [DatabaseClient]\nStack trace:\r\n\t\tat io.r2dbc.mssql.ExceptionFactory.createException(ExceptionFactory.java:148)\r\n\t\tat io.r2dbc.mssql.ExceptionFactory.createException(ExceptionFactory.java:181)\r\n\t\tat io.r2dbc.mssql.RpcQueryMessageFlow.lambda$exchange$6(RpcQueryMessageFlow.java:212)\r\n\t\tat reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onNext(FluxHandle.java:303)\r\n\t\tat reactor.core.publisher.EmitterProcessor.drain(EmitterProcessor.java:426)\r\n\t\tat reactor.core.publisher.EmitterProcessor.onNext(EmitterProcessor.java:268)\r\n\t\tat reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:192)\r\n\t\tat reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:192)\r\n\t\tat reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:192)\r\n\t\tat reactor.core.publisher.FluxHandle$HandleSubscriber.onNext(FluxHandle.java:112)\r\n\t\tat reactor.core.publisher.MonoFlatMapMany$FlatMapManyInner.onNext(MonoFlatMapMany.java:242)\r\n\t\tat reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:192)\r\n\t\tat reactor.core.publisher.EmitterProcessor.drain(EmitterProcessor.java:426)\r\n\t\tat reactor.core.publisher.EmitterProcessor.onNext(EmitterProcessor.java:268)\r\n\t\tat io.r2dbc.mssql.client.ReactorNettyClient$1.next(ReactorNettyClient.java:237)\r\n\t\tat io.r2dbc.mssql.client.ReactorNettyClient$1.next(ReactorNettyClient.java:197)\r\n\t\tat io.r2dbc.mssql.message.token.Tabular$TabularDecoder.decode(Tabular.java:425)\r\n\t\tat io.r2dbc.mssql.client.ConnectionState$4$1.decode(ConnectionState.java:206)\r\n\t\tat io.r2dbc.mssql.client.StreamDecoder.withState(StreamDecoder.java:137)\r\n\t\tat io.r2dbc.mssql.client.StreamDecoder.decode(StreamDecoder.java:109)\r\n\t\tat io.r2dbc.mssql.client.ReactorNettyClient.lambda$new$6(ReactorNettyClient.java:247)\r\n\t\tat reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:177)\r\n\t\tat reactor.netty.channel.FluxReceive.drainReceiver(FluxReceive.java:218)\r\n\t\tat reactor.netty.channel.FluxReceive.onInboundNext(FluxReceive.java:351)\r\n\t\tat reactor.netty.channel.ChannelOperations.onInboundNext(ChannelOperations.java:348)\r\n\t\tat reactor.netty.channel.ChannelOperationsHandler.channelRead(ChannelOperationsHandler.java:90)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:377)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:363)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:355)\r\n\t\tat io.netty.channel.ChannelInboundHandlerAdapter.channelRead(ChannelInboundHandlerAdapter.java:93)\r\n\t\tat io.r2dbc.mssql.client.ssl.TdsSslHandler.channelRead(TdsSslHandler.java:402)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:377)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:363)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:355)\r\n\t\tat io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1410)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:377)\r\n\t\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:363)\r\n\t\tat io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:919)\r\n\t\tat io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)\r\n\t\tat io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:714)\r\n\t\tat io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650)\r\n\t\tat io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576)\r\n\t\tat io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)\r\n\t\tat io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)\r\n\t\tat io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)\r\n\t\tat io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)\r\n\t\tat java.base/java.lang.Thread.run(Unknown Source)\r\n"}
```