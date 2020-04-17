package com.github.guigumua.robot.starter.client.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.common.event.EventType;
import com.github.guigumua.robot.common.event.message.DiscussMessageEvent;
import com.github.guigumua.robot.common.event.message.GroupMessageEvent;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.event.notice.FriendAddNoticeEvent;
import com.github.guigumua.robot.common.event.request.GroupAddInviteRequestEvent;
import com.github.guigumua.robot.common.exception.RespondTimeOutExcption;
import com.github.guigumua.robot.common.request.CanSendImageRequest;
import com.github.guigumua.robot.common.request.CanSendRecordRequest;
import com.github.guigumua.robot.common.request.CoolQRequest;
import com.github.guigumua.robot.common.request.CoolQRequest.Response;
import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.DeleteMsgRequest;
import com.github.guigumua.robot.common.request.GetCookiesRequest;
import com.github.guigumua.robot.common.request.GetCredentialsRequest;
import com.github.guigumua.robot.common.request.GetCrsfTokenRequest;
import com.github.guigumua.robot.common.request.GetFriendListRequest;
import com.github.guigumua.robot.common.request.GetGroupInfoRequest;
import com.github.guigumua.robot.common.request.GetGroupListRequest;
import com.github.guigumua.robot.common.request.GetGroupMemberInfoRequest;
import com.github.guigumua.robot.common.request.GetGroupMemberListRequest;
import com.github.guigumua.robot.common.request.GetImageRequest;
import com.github.guigumua.robot.common.request.GetLoginInfoRequest;
import com.github.guigumua.robot.common.request.GetRecordRequest;
import com.github.guigumua.robot.common.request.GetStrangerInfoRequest;
import com.github.guigumua.robot.common.request.SendLikeRequest;
import com.github.guigumua.robot.common.request.SendMsgRequest;
import com.github.guigumua.robot.common.request.SetDiscussLeaveRequest;
import com.github.guigumua.robot.common.request.SetFriendAddRequestRequest;
import com.github.guigumua.robot.common.request.SetGroupAddRequestRequest;
import com.github.guigumua.robot.common.request.SetGroupAdminRequest;
import com.github.guigumua.robot.common.request.SetGroupAnonymousBanRequest;
import com.github.guigumua.robot.common.request.SetGroupAnonymousBanRequest.Anonymous;
import com.github.guigumua.robot.common.request.SetGroupAnonymousRequest;
import com.github.guigumua.robot.common.request.SetGroupBanRequest;
import com.github.guigumua.robot.common.request.SetGroupCardRequest;
import com.github.guigumua.robot.common.request.SetGroupKickRequest;
import com.github.guigumua.robot.common.request.SetGroupLeaveRequest;
import com.github.guigumua.robot.common.request.SetGroupSpecialTitleRequest;
import com.github.guigumua.robot.common.request.SetGroupWholeBanRequest;
import com.github.guigumua.robot.common.request._GetFriendListRequset;
import com.github.guigumua.robot.common.request._GetFriendListRequset.FlatResponseData;
import com.github.guigumua.robot.common.request._GetGroupInfoRequest;
import com.github.guigumua.robot.common.request._GetGroupNoticeRequest;
import com.github.guigumua.robot.common.request._GetVipInfoRequest;
import com.github.guigumua.robot.common.request._SendGroupNoticeRequest;
import com.github.guigumua.robot.common.request.ws.CanSendImageWsRequest;
import com.github.guigumua.robot.common.request.ws.CanSendRecordWsRequest;
import com.github.guigumua.robot.common.request.ws.DeleteMsgWsRequest;
import com.github.guigumua.robot.common.request.ws.GetCookiesWsRequest;
import com.github.guigumua.robot.common.request.ws.GetCredentialsWsRequest;
import com.github.guigumua.robot.common.request.ws.GetCrsfTokenWsRequest;
import com.github.guigumua.robot.common.request.ws.GetFriendListWsRequest;
import com.github.guigumua.robot.common.request.ws.GetGroupInfoWsRequest;
import com.github.guigumua.robot.common.request.ws.GetGroupListWsRequest;
import com.github.guigumua.robot.common.request.ws.GetGroupMemberInfoWsRequest;
import com.github.guigumua.robot.common.request.ws.GetGroupMemberListWsRequest;
import com.github.guigumua.robot.common.request.ws.GetImageWsRequest;
import com.github.guigumua.robot.common.request.ws.GetLoginInfoWsRequest;
import com.github.guigumua.robot.common.request.ws.GetRecordWsRequest;
import com.github.guigumua.robot.common.request.ws.GetStrangerInfoWsRequest;
import com.github.guigumua.robot.common.request.ws.SendLikeWsRequest;
import com.github.guigumua.robot.common.request.ws.SendMsgWsRequest;
import com.github.guigumua.robot.common.request.ws.SetDiscussLeaveWsRequest;
import com.github.guigumua.robot.common.request.ws.SetFriendAddRequestWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupAddRequestWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupAdminWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupAnonymousBanWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupAnonymousWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupBanWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupCardWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupKickWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupLeaveWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupSpecialTitleWsRequest;
import com.github.guigumua.robot.common.request.ws.SetGroupWholeBanWsRequest;
import com.github.guigumua.robot.common.request.ws._GetFriendListWsRequest;
import com.github.guigumua.robot.common.request.ws._GetGroupInfoWsRequest;
import com.github.guigumua.robot.common.request.ws._GetGroupNoticeWsRequest;
import com.github.guigumua.robot.common.request.ws._GetVipInfoWsRequest;
import com.github.guigumua.robot.common.request.ws._SendGroupNoticeWsRequest;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.client.RobotClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

public class RobotWebSocketClient implements RobotClient {
	private static final Logger logger = LoggerFactory.getLogger(RobotWebSocketClient.class);
	private final NioEventLoopGroup group = new NioEventLoopGroup();
	private Bootstrap bootstrap = new Bootstrap();
	private ChannelInitializer<SocketChannel> pipeline = new RobotWebSocketClientPipeline();
	private final String host;
	private final int port;
	private final boolean useWs = true;
	private WebSocketClientHandshaker handshaker;
	private ChannelPromise handshakeFuture;
	private Channel channel;
	private boolean async = true;
	private URI uri;

	public RobotWebSocketClient(String host, int port) {
		bootstrap.group(group).channel(NioSocketChannel.class).handler(pipeline);
		this.host = host;
		this.port = port;
		connect();
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	private void connect() {
		try {
			HttpHeaders httpHeaders = new DefaultHttpHeaders();
			this.uri = new URI("ws://" + host + ":" + port + "/api");
			// 进行握手
			WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri,
					WebSocketVersion.V13, (String) null, true, httpHeaders);
			// 需要协议的host和port
			Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
			this.handshaker = handshaker;
			handshaker.handshake(channel);
			Thread.sleep(1000);
			this.handshakeFuture.sync();
		} catch (URISyntaxException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isUseWs() {
		return useWs;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	private CoolQWebSocketRequest.Response<?> send0(CoolQWebSocketRequest request) {
		if (async) {
			this.requestAsync(request);
		} else {
			this.request(request);
		}
		return request.getResponse();
	}

	/**
	 * 发送消息，默认异步执行
	 * 
	 * @param request coolq API接收的请求格式的封装对象
	 * @return 发送结果响应，异步执行时响应为null
	 */
	public SendMsgRequest.Response sendMsg(SendMsgWsRequest request) {
		return ((SendMsgRequest.Response) send0(request));
	}

	@Override
	public SendMsgRequest.Response sendMsg(MessageEvent e, String message) {
		EventType type = e.getEventType();
		if (type == EventType.PRIVATE_MESSAGE) {
			return this.sendPrivateMsg(e.getUserId(), message);
		} else if (type == EventType.GROUP_MESSAGE) {
			return this.sendGroupMsg(((GroupMessageEvent) e).getGroupId(), message);
		} else if (type == EventType.DISCUSS_MESSAGE) {
			return this.sendDiscussMsg(((DiscussMessageEvent) e).getDiscussId(), message);
		}
		return null;
	}

	@Override
	public SendMsgRequest.Response sendPrivateMsg(long userId, String message) {
		SendMsgWsRequest request = new SendMsgWsRequest();
		request.setMessageType(SendMsgRequest.PRIVATE_TYPE);
		request.setUserId(userId);
		request.setMessage(message);
		return (SendMsgRequest.Response) send0(request);
	}

	@Override
	public SendMsgRequest.Response sendGroupMsg(long groupId, String message) {
		SendMsgWsRequest request = new SendMsgWsRequest();
		request.setMessageType(SendMsgWsRequest.GROUP_TYPE);
		request.setGroupId(groupId);
		request.setMessage(message);
		return (SendMsgRequest.Response) send0(request);
	}

	@Override
	public void sendGroupAndDelete(long groupId, String message, int delay, TimeUnit unit) {
		SendMsgWsRequest request = new SendMsgWsRequest();
		request.setMessageType(SendMsgRequest.GROUP_TYPE);
		request.setGroupId(groupId);
		request.setMessage(message);
		SendMsgRequest.Response response = (SendMsgRequest.Response) this.request(request);
		SendMsgRequest.ResponseData data = response.getData();
		int messageId = data.getMessageId();
		group.schedule(() -> {
			deleteMsg(messageId);
		}, delay, unit);
	}

	@Override
	public SendMsgRequest.Response sendDiscussMsg(long discussId, String message) {
		SendMsgWsRequest request = new SendMsgWsRequest();
		request.setMessageType(SendMsgRequest.DISCUSS_TYPE);
		request.setGroupId(discussId);
		request.setMessage(message);
		this.requestAsync(request);
		return (SendMsgRequest.Response) send0(request);
	}

	@Override
	public SendLikeRequest.Response sendLike(long userId, int times) {
		SendLikeWsRequest request = new SendLikeWsRequest();
		request.setUserId(userId);
		request.setTimes(times);
		return (SendLikeRequest.Response) send0(request);
	}

	@Override
	public DeleteMsgRequest.Response deleteMsg(int messageId) {
		DeleteMsgWsRequest request = new DeleteMsgWsRequest();
		request.setMessageId(messageId);
		return (DeleteMsgRequest.Response) send0(request);
	}

	@Override
	public SetGroupKickRequest.Response groupKick(long groupId, long userId) {
		return setGroupKick(groupId, userId, false);
	}

	@Override
	public SetGroupKickRequest.Response setGroupKick(long groupId, long userId, boolean reject) {
		SetGroupKickWsRequest request = new SetGroupKickWsRequest();
		request.setGroupId(groupId);
		request.setUserId(userId);
		request.setRejectAddRequest(reject);
		return (SetGroupKickRequest.Response) send0(request);
	}

	@Override
	public SetGroupBanRequest.Response setGroupBan(long groupId, long userId, int duration) {
		SetGroupBanWsRequest request = new SetGroupBanWsRequest();
		request.setDuration(duration);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (SetGroupBanRequest.Response) send0(request);
	}

	@Override
	public SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, Anonymous anonymous, String flag,
			int duration) {
		SetGroupAnonymousBanWsRequest request = new SetGroupAnonymousBanWsRequest();
		request.setAnonymous(anonymous);
		request.setDuration(duration);
		request.setFlag(flag);
		request.setGroupId(groupId);
		return (SetGroupAnonymousBanRequest.Response) send0(request);
	}

	@Override
	public SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, GroupMessageEvent e, int duration) {
		SetGroupAnonymousBanWsRequest request = new SetGroupAnonymousBanWsRequest();
		Anonymous anonymous = new Anonymous();
		anonymous.setFlag(e.getAnonymous().getFlag());
		anonymous.setId(e.getAnonymous().getId());
		anonymous.setName(e.getAnonymous().getName());
		request.setAnonymous(anonymous);
		request.setDuration(duration);
		request.setFlag(e.getAnonymous().getFlag());
		request.setGroupId(groupId);
		return (SetGroupAnonymousBanRequest.Response) send0(request);
	}

	@Override
	public SetGroupWholeBanRequest.Response setGroupWholeBan(long groupId, boolean enable) {
		SetGroupWholeBanWsRequest request = new SetGroupWholeBanWsRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		return (SetGroupWholeBanRequest.Response) send0(request);
	}

	@Override
	public SetGroupAdminRequest.Response setGroupAdmin(long groupId, long userId, boolean enable) {
		SetGroupAdminWsRequest request = new SetGroupAdminWsRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (SetGroupAdminRequest.Response) send0(request);
	}

	@Override
	public SetGroupAnonymousRequest.Response setGroupAnonymous(long groupId, boolean enable) {
		SetGroupAnonymousWsRequest request = new SetGroupAnonymousWsRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		return (SetGroupAnonymousRequest.Response) send0(request);
	}

	@Override
	public SetGroupCardRequest.Response setGroupCard(long groupId, long userId, String card) {
		SetGroupCardWsRequest request = new SetGroupCardWsRequest();
		request.setCard(card);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (SetGroupCardRequest.Response) send0(request);
	}

	@Override
	public SetGroupLeaveRequest.Response setGroupLeave(long groupId, boolean isDismiss) {
		SetGroupLeaveWsRequest request = new SetGroupLeaveWsRequest();
		request.setDismiss(isDismiss);
		request.setGroupId(groupId);
		return (SetGroupLeaveRequest.Response) send0(request);
	}

	@Override
	public SetGroupSpecialTitleRequest.Response setGroupSpecialTitle(long groupId, long userId, String specialTitle) {
		SetGroupSpecialTitleWsRequest request = new SetGroupSpecialTitleWsRequest();
		request.setDuration(-1);
		request.setGroupId(groupId);
		request.setSpecialTitle(specialTitle);
		request.setUserId(userId);
		return (SetGroupSpecialTitleRequest.Response) send0(request);
	}

	@Override
	public SetDiscussLeaveRequest.Response setDiscussLeave(long discussId) {
		SetDiscussLeaveWsRequest request = new SetDiscussLeaveWsRequest();
		request.setDiscussId(discussId);
		return (SetDiscussLeaveRequest.Response) send0(request);
	}

	@Override
	public SetFriendAddRequestRequest.Response setFriendAddRequest(String flag, boolean approve, String remark) {
		SetFriendAddRequestWsRequest request = new SetFriendAddRequestWsRequest();
		request.setApprove(approve);
		request.setFlag(flag);
		request.setRemark(remark);
		return (SetFriendAddRequestRequest.Response) send0(request);
	}

	@Override
	public SetFriendAddRequestRequest.Response setFriendAddRequest(FriendAddNoticeEvent e, boolean approve,
			String remark) {
		SetFriendAddRequestWsRequest request = new SetFriendAddRequestWsRequest();
		request.setApprove(approve);
		request.setFlag(e.getFlag());
		request.setRemark(remark);
		return (SetFriendAddRequestRequest.Response) send0(request);
	}

	@Override
	public SetGroupAddRequestRequest.Response setGroupAddRequest(GroupAddInviteRequestEvent e, boolean approve,
			String reason) {
		SetGroupAddRequestWsRequest request = new SetGroupAddRequestWsRequest();
		request.setApprove(approve);
		request.setFlag(e.getFlag());
		request.setReason(reason);
		request.setType(e.getSubType());
		return (SetGroupAddRequestRequest.Response) send0(request);
	}

	@Override
	public GetLoginInfoRequest.ResponseData getLoginInfo() {
		return ((GetLoginInfoRequest.Response) request(new GetLoginInfoWsRequest())).getData();
	}

	@Override
	public GetStrangerInfoRequest.ResponseData getStrangerInfo(long userId) {
		GetStrangerInfoWsRequest request = new GetStrangerInfoWsRequest();
		request.setUserId(userId);
		return ((GetStrangerInfoRequest.Response) this.request(request)).getData();
	}

	@Override
	public List<GetFriendListRequest.ResponseData> getFriendList() {
		return ((GetFriendListRequest.Response) this.request(new GetFriendListWsRequest())).getData();
	}

	@Override
	public List<GetGroupListRequest.ResponseData> getGroupList() {
		return ((GetGroupListRequest.Response) this.request(new GetGroupListWsRequest())).getData();
	}

	@Override
	public GetGroupInfoRequest.ResponseData getGroupInfo(long groupId) {
		GetGroupInfoWsRequest request = new GetGroupInfoWsRequest();
		request.setGroupId(groupId);
		return ((GetGroupInfoRequest.Response) this.request(request)).getData();
	}

	@Override
	public GetGroupMemberInfoRequest.ResponseData getGroupMemberInfo(long groupId, long userId) {
		GetGroupMemberInfoWsRequest request = new GetGroupMemberInfoWsRequest();
		request.setGroupId(groupId);
		request.setUserId(userId);
		return ((GetGroupMemberInfoRequest.Response) this.request(request)).getData();
	}

	@Override
	public List<GetGroupMemberInfoRequest.ResponseData> getGroupMemberList(long groupId) {
		GetGroupMemberListWsRequest request = new GetGroupMemberListWsRequest();
		request.setGroupId(groupId);
		return ((GetGroupMemberListRequest.Response) this.request(request)).getData();
	}

	@Override
	public String getCookies(String domain) {
		GetCookiesWsRequest request = new GetCookiesWsRequest();
		request.setDomain(domain);
		return ((GetCookiesRequest.Response) this.request(request)).getData().getCookies();
	}

	@Override
	public int getCrsfToken() {
		return ((GetCrsfTokenRequest.Response) this.request(new GetCrsfTokenWsRequest())).getData().getToken();
	}

	@Override
	public GetCredentialsRequest.ResponseData getCredentials(String domain) {
		return ((GetCredentialsRequest.Response) this.request(new GetCredentialsWsRequest())).getData();
	}

	@Override
	public String getRecord(String file, String outFormat, boolean fullPath) {
		GetRecordWsRequest request = new GetRecordWsRequest();
		request.setFile(file);
		request.setFullPath(fullPath);
		request.setOutFormat(outFormat);
		return ((GetRecordRequest.Response) this.request(request)).getData().getFile();
	}

	@Override
	public GetImageRequest.Response getImage(String file) {
		GetImageWsRequest request = new GetImageWsRequest();
		request.setFile(file);
		return (GetImageRequest.Response) this.request(request);
	}

	@Override
	public boolean canSendImage() {
		return ((CanSendImageRequest.Response) this.request(new CanSendImageWsRequest())).getData().isYes();
	}

	@Override
	public boolean canSendRecord() {
		return ((CanSendRecordRequest.Response) this.request(new CanSendRecordWsRequest())).getData().isYes();
	}

	@Override
	public List<_GetFriendListRequset.ResponseData> _getFriendList() {
		_GetFriendListWsRequest request = new _GetFriendListWsRequest();
		request.setFlat(false);
		return ((_GetFriendListRequset.Response) this.request(request)).getData();
	}

	@Override
	public FlatResponseData _getFlatFriendList() {
		_GetFriendListWsRequest request = new _GetFriendListWsRequest();
		request.setFlat(true);
		return ((_GetFriendListRequset.FlatResponse) this.request(request)).getData();
	}

	@Override
	public _GetGroupInfoRequest.ResponseData _getGroupInfo(long groupId) {
		_GetGroupInfoWsRequest request = new _GetGroupInfoWsRequest();
		request.setGroupId(groupId);
		return ((_GetGroupInfoRequest.Response) this.request(request)).getData();
	}

	@Override
	public _GetVipInfoRequest.ResponseData _getVipInfo(long userId) {
		_GetVipInfoWsRequest request = new _GetVipInfoWsRequest();
		request.setUserId(userId);
		return (_GetVipInfoRequest.ResponseData) this.request(request).getData();
	}

	@Override
	public List<_GetGroupNoticeRequest.ResponseData> _getGroupNotice(long groupId) {
		_GetGroupNoticeWsRequest request = new _GetGroupNoticeWsRequest();
		request.setGroupId(groupId);
		return ((_GetGroupNoticeRequest.Response) request(request)).getData();
	}

	@Override
	public _SendGroupNoticeRequest.Response _sendGroupNotice(long groupId, String title, String content) {
		_SendGroupNoticeWsRequest request = new _SendGroupNoticeWsRequest();
		request.setContent(content);
		request.setGroupId(groupId);
		request.setTitle(title);
		return (_SendGroupNoticeRequest.Response) this.send0(request);
	}

	private volatile int i = 0;
	private volatile int j = 0;

	public void requestAsync(CoolQWebSocketRequest request) {
		AttributeKey<CoolQWebSocketRequest> key = AttributeKey.valueOf(String.valueOf(i++));
		channel.attr(key).set(request);
		TextWebSocketFrame frame = request.getRequest();
		channel.writeAndFlush(frame);
	}

	/**
	 * 核心同步api
	 * 
	 * @param request
	 * @return
	 */
	public Response<?> request(CoolQWebSocketRequest request) {
		long start = System.currentTimeMillis();
		requestAsync(request);
		for (; request.getResponse() == null;) {
			try {
				if (System.currentTimeMillis() - start < 10000) {
					Thread.sleep(1);
				} else {
					throw new RespondTimeOutExcption("请求" + request.getAction() + "响应超时");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return request.getResponse();
	}

	private class RobotWebSocketClientPipeline extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline p = ch.pipeline();
			p.addLast(new HttpClientCodec(), new HttpObjectAggregator(1 << 20));
			p.addLast("handler", new RobotWebSocketClientHandler());
		}
	}

	private class RobotWebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

		@Override
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
			handshakeFuture = ctx.newPromise();
			channel = ctx.channel();
		}

		@Override
		public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
			Channel ch = ctx.channel();
			FullHttpResponse response;
			// 判断接收的请求是否是牵手
			if (!handshaker.isHandshakeComplete()) {
				try {
					response = (FullHttpResponse) msg;
					// 握手协议返回，设置结束握手
					handshaker.finishHandshake(ch, response);
					// 设置成功
					handshakeFuture.setSuccess();
					logger.debug("连接coolq api的websocket连接牵手成功！uri：{}", uri);
				} catch (WebSocketHandshakeException var7) {
					FullHttpResponse res = (FullHttpResponse) msg;
					String errorMsg = String.format("WebSocket客户端连接失败，状态为:%s", res.status());
					handshakeFuture.setFailure(new Exception(errorMsg));
				}
			} else if (msg instanceof FullHttpResponse) {
				response = (FullHttpResponse) msg;
				// 可以吧字符码转为指定类型
				// this.listener.onFail(response.status().code(),
				// response.content().toString(CharsetUtil.UTF_8));
				throw new IllegalStateException("未预料的错误(getStatus=" + response.status() + ", content="
						+ response.content().toString(CharsetUtil.UTF_8) + ')');
			} else {// 如果不是牵手
				WebSocketFrame frame = (WebSocketFrame) msg;
				if (frame instanceof TextWebSocketFrame) {
					AttributeKey<CoolQWebSocketRequest> key = AttributeKey.valueOf(String.valueOf(j++));
					CoolQWebSocketRequest request = channel.attr(key).getAndSet(null);
					TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
					String json = textFrame.content().toString(CharsetUtil.UTF_8);
					CoolQRequest.Response<?> res = JsonUtil.toBean(json, request.getResponseClass());
					request.setResponse(res);
					logger.debug("{}请求完成,响应：{}", request.getAction(), res.toString());
				} else if (frame instanceof BinaryWebSocketFrame) {
//					System.out.println("二进制WebSocketFrame");
				} else if (frame instanceof PongWebSocketFrame) {
					// 返回心跳监测
					// System.out.println("WebSocket客户端接收到pong");
				} else if (frame instanceof CloseWebSocketFrame) {
//					System.out.println("接收关闭贞");
					ch.close();
				}
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
		}

	}
}
