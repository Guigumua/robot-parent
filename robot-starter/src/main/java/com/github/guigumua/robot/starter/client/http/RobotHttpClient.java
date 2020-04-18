package com.github.guigumua.robot.starter.client.http;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.CoolQRequest;
import com.github.guigumua.robot.common.request.CoolQRequest.Response;
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
import com.github.guigumua.robot.common.request.http.CanSendImageHttpRequest;
import com.github.guigumua.robot.common.request.http.CanSendRecordHttpRequest;
import com.github.guigumua.robot.common.request.http.DeleteMsgHttpRequest;
import com.github.guigumua.robot.common.request.http.GetCookiesHttpRequest;
import com.github.guigumua.robot.common.request.http.GetCredentialsHttpRequest;
import com.github.guigumua.robot.common.request.http.GetCrsfTokenHttpRequest;
import com.github.guigumua.robot.common.request.http.GetFriendListHttpRequest;
import com.github.guigumua.robot.common.request.http.GetGroupInfoHttpRequest;
import com.github.guigumua.robot.common.request.http.GetGroupListHttpRequest;
import com.github.guigumua.robot.common.request.http.GetGroupMemberInfoHttpRequest;
import com.github.guigumua.robot.common.request.http.GetGroupMemberListHttpRequest;
import com.github.guigumua.robot.common.request.http.GetImageHttpRequest;
import com.github.guigumua.robot.common.request.http.GetLoginInfoHttpRequest;
import com.github.guigumua.robot.common.request.http.GetRecordHttpRequest;
import com.github.guigumua.robot.common.request.http.GetStrangerInfoHttpRequest;
import com.github.guigumua.robot.common.request.http.SendLikeHttpRequest;
import com.github.guigumua.robot.common.request.http.SendMsgHttpRequest;
import com.github.guigumua.robot.common.request.http.SetDiscussLeaveHttpRequest;
import com.github.guigumua.robot.common.request.http.SetFriendAddRequestHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupAddRequestHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupAdminHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupAnonymousBanHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupAnonymousHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupBanHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupCardHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupKickHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupLeaveHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupSpecialTitleHttpRequest;
import com.github.guigumua.robot.common.request.http.SetGroupWholeBanHttpRequest;
import com.github.guigumua.robot.common.request.http._GetFriendListHttpRequest;
import com.github.guigumua.robot.common.request.http._GetGroupInfoHttpRequest;
import com.github.guigumua.robot.common.request.http._GetGroupNoticeHttpRequest;
import com.github.guigumua.robot.common.request.http._GetVipInfoHttpRequest;
import com.github.guigumua.robot.common.request.http._SendGroupNoticeHttpRequest;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.client.RobotClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

public class RobotHttpClient implements RobotClient {
	private static final Logger logger = LoggerFactory.getLogger(RobotHttpClient.class);
	private final NioEventLoopGroup group = new NioEventLoopGroup();
	private final Bootstrap bootstrap = new Bootstrap();
	private final String host;
	private final int port;
	private boolean async = true;
	/**
	 * 连接失败重连次数
	 */
	private int reconnectTimes = 3;
	/**
	 * 重连滞后时间，单位毫秒 eg:设定为5000，第一次重连在5秒后，第二次重连在10秒后，第三次重连在20秒后。
	 */
	private int reconnectDelayTime = 5000;
	/**
	 * 响应超时时间
	 */
	private int maxRespondTime = 10000;

	public RobotHttpClient(String host, int port) {
		ChannelInitializer<SocketChannel> pipeline = new RobotHttpClientPipeline();
		bootstrap.group(group).channel(NioSocketChannel.class).handler(pipeline);
		this.host = host;
		this.port = port;
	}

	public int getReconnectDelayTime() {
		return reconnectDelayTime;
	}

	public void setReconnectDelayTime(int reconnectDelayTime) {
		this.reconnectDelayTime = reconnectDelayTime;
	}

	public int getMaxRespondTime() {
		return maxRespondTime;
	}

	public void setMaxRespondTime(int maxRespondTime) {
		this.maxRespondTime = maxRespondTime;
	}

	public int getReconnectTimes() {
		return reconnectTimes;
	}

	public void setReconnectTimes(int reconnectTimes) {
		this.reconnectTimes = reconnectTimes;
	}

	@Override
	public boolean isUseWs() {
		return false;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	private final AtomicInteger times = new AtomicInteger(0);
	private void connect() {
		ChannelFuture future = bootstrap.connect(host, port);
		future.addListener((ChannelFuture f) -> {
			if (f.isSuccess()) {
				times.set(0);
			} else {
				logger.warn("连接失败");
				if (times.incrementAndGet() >= reconnectTimes) {
					logger.warn("连接重试超时");
					requestQueue.poll();
					throw new ConnectTimeoutException(String.format("连接%s:%d重试%d次超时！", host, port, times.get()));
				} else {
					group.schedule(this::connect, ((1 << times.get()) - 1) * reconnectDelayTime, TimeUnit.MILLISECONDS);
				}
			}
		});
	}

	/**
	 * 请求任务队列
	 */
	private final ConcurrentLinkedQueue<CoolQHttpRequest> requestQueue = new ConcurrentLinkedQueue<>();

	private CoolQHttpRequest.Response<?> send0(CoolQHttpRequest request) {
		if (async) {
			this.requestAsync(request);
		} else {
			this.request(request);
		}
		return request.getResponse();
	}

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


	public SendMsgRequest.Response sendPrivateMsg(long userId, String message) {
		SendMsgHttpRequest request = new SendMsgHttpRequest();
		request.setMessageType(SendMsgRequest.PRIVATE_TYPE);
		request.setUserId(userId);
		request.setMessage(message);
		return (SendMsgRequest.Response) send0(request);
	}


	public SendMsgRequest.Response sendGroupMsg(long groupId, String message) {
		SendMsgHttpRequest request = new SendMsgHttpRequest();
		request.setMessageType(SendMsgHttpRequest.GROUP_TYPE);
		request.setGroupId(groupId);
		request.setMessage(message);
		return (SendMsgRequest.Response) send0(request);
	}


	public void sendGroupAndDelete(long groupId, String message, int delay, TimeUnit unit) {
		SendMsgHttpRequest request = new SendMsgHttpRequest();
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


	public SendMsgRequest.Response sendDiscussMsg(long discussId, String message) {
		SendMsgHttpRequest request = new SendMsgHttpRequest();
		request.setMessageType(SendMsgRequest.DISCUSS_TYPE);
		request.setGroupId(discussId);
		request.setMessage(message);
		this.requestAsync(request);
		return (SendMsgRequest.Response) send0(request);
	}


	public SendLikeRequest.Response sendLike(long userId, int times) {
		SendLikeHttpRequest request = new SendLikeHttpRequest();
		request.setUserId(userId);
		request.setTimes(times);
		return (SendLikeRequest.Response) send0(request);
	}

	public DeleteMsgRequest.Response deleteMsg(int messageId) {
		DeleteMsgHttpRequest request = new DeleteMsgHttpRequest();
		request.setMessageId(messageId);
		return (DeleteMsgRequest.Response) send0(request);
	}
	public SetGroupKickRequest.Response groupKick(long groupId, long userId) {
		return setGroupKick(groupId, userId, false);
	}

	public SetGroupKickRequest.Response setGroupKick(long groupId, long userId, boolean reject) {
		SetGroupKickHttpRequest request = new SetGroupKickHttpRequest();
		request.setGroupId(groupId);
		request.setUserId(userId);
		request.setRejectAddRequest(reject);
		return (SetGroupKickRequest.Response) send0(request);
	}

	public SetGroupBanRequest.Response setGroupBan(long groupId, long userId, int duration) {
		SetGroupBanHttpRequest request = new SetGroupBanHttpRequest();
		request.setDuration(duration);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (SetGroupBanRequest.Response) send0(request);
	}

	public SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, Anonymous anonymous, String flag,
			int duration) {
		SetGroupAnonymousBanHttpRequest request = new SetGroupAnonymousBanHttpRequest();
		request.setAnonymous(anonymous);
		request.setDuration(duration);
		request.setFlag(flag);
		request.setGroupId(groupId);
		return (SetGroupAnonymousBanRequest.Response) send0(request);
	}

	public SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, GroupMessageEvent e, int duration) {
		SetGroupAnonymousBanHttpRequest request = new SetGroupAnonymousBanHttpRequest();
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

	public SetGroupWholeBanRequest.Response setGroupWholeBan(long groupId, boolean enable) {
		SetGroupWholeBanHttpRequest request = new SetGroupWholeBanHttpRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		return (SetGroupWholeBanRequest.Response) send0(request);
	}

	public SetGroupAdminRequest.Response setGroupAdmin(long groupId, long userId, boolean enable) {
		SetGroupAdminHttpRequest request = new SetGroupAdminHttpRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (SetGroupAdminRequest.Response) send0(request);
	}

	public SetGroupAnonymousRequest.Response setGroupAnonymous(long groupId, boolean enable) {
		SetGroupAnonymousHttpRequest request = new SetGroupAnonymousHttpRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		return (SetGroupAnonymousRequest.Response) send0(request);
	}


	public SetGroupCardRequest.Response setGroupCard(long groupId, long userId, String card) {
		SetGroupCardHttpRequest request = new SetGroupCardHttpRequest();
		request.setCard(card);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (SetGroupCardRequest.Response) send0(request);
	}


	public SetGroupLeaveRequest.Response setGroupLeave(long groupId, boolean isDismiss) {
		SetGroupLeaveHttpRequest request = new SetGroupLeaveHttpRequest();
		request.setDismiss(isDismiss);
		request.setGroupId(groupId);
		return (SetGroupLeaveRequest.Response) send0(request);
	}


	public SetGroupSpecialTitleRequest.Response setGroupSpecialTitle(long groupId, long userId, String specialTitle) {
		SetGroupSpecialTitleHttpRequest request = new SetGroupSpecialTitleHttpRequest();
		request.setDuration(-1);
		request.setGroupId(groupId);
		request.setSpecialTitle(specialTitle);
		request.setUserId(userId);
		return (SetGroupSpecialTitleRequest.Response) send0(request);
	}


	public SetDiscussLeaveRequest.Response setDiscussLeave(long discussId) {
		SetDiscussLeaveHttpRequest request = new SetDiscussLeaveHttpRequest();
		request.setDiscussId(discussId);
		return (SetDiscussLeaveRequest.Response) send0(request);
	}


	public SetFriendAddRequestRequest.Response setFriendAddRequest(String flag, boolean approve, String remark) {
		SetFriendAddRequestHttpRequest request = new SetFriendAddRequestHttpRequest();
		request.setApprove(approve);
		request.setFlag(flag);
		request.setRemark(remark);
		return (SetFriendAddRequestRequest.Response) send0(request);
	}


	public SetFriendAddRequestRequest.Response setFriendAddRequest(FriendAddNoticeEvent e, boolean approve,
			String remark) {
		SetFriendAddRequestHttpRequest request = new SetFriendAddRequestHttpRequest();
		request.setApprove(approve);
		request.setFlag(e.getFlag());
		request.setRemark(remark);
		return (SetFriendAddRequestRequest.Response) send0(request);
	}

	public SetGroupAddRequestRequest.Response setGroupAddRequest(GroupAddInviteRequestEvent e, boolean approve,
			String reason) {
		SetGroupAddRequestHttpRequest request = new SetGroupAddRequestHttpRequest();
		request.setApprove(approve);
		request.setFlag(e.getFlag());
		request.setReason(reason);
		request.setType(e.getSubType());
		return (SetGroupAddRequestRequest.Response) send0(request);
	}

	// 上面的方法默认异步

	// 下面的方法只能同步

	public GetLoginInfoRequest.ResponseData getLoginInfo() {
		return ((GetLoginInfoRequest.Response) request(new GetLoginInfoHttpRequest())).getData();
	}

	public GetStrangerInfoRequest.ResponseData getStrangerInfo(long userId) {
		GetStrangerInfoHttpRequest request = new GetStrangerInfoHttpRequest();
		request.setUserId(userId);
		return ((GetStrangerInfoRequest.Response) this.request(request)).getData();
	}


	public List<GetFriendListRequest.ResponseData> getFriendList() {
		return ((GetFriendListRequest.Response) this.request(new GetFriendListHttpRequest())).getData();
	}


	public List<GetGroupListRequest.ResponseData> getGroupList() {
		return ((GetGroupListRequest.Response) this.request(new GetGroupListHttpRequest())).getData();
	}


	public GetGroupInfoRequest.ResponseData getGroupInfo(long groupId) {
		GetGroupInfoHttpRequest request = new GetGroupInfoHttpRequest();
		request.setGroupId(groupId);
		return ((GetGroupInfoRequest.Response) this.request(request)).getData();
	}


	public GetGroupMemberInfoRequest.ResponseData getGroupMemberInfo(long groupId, long userId) {
		GetGroupMemberInfoHttpRequest request = new GetGroupMemberInfoHttpRequest();
		request.setGroupId(groupId);
		request.setUserId(userId);
		return ((GetGroupMemberInfoRequest.Response) this.request(request)).getData();
	}

	public List<GetGroupMemberInfoRequest.ResponseData> getGroupMemberList(long groupId) {
		GetGroupMemberListHttpRequest request = new GetGroupMemberListHttpRequest();
		request.setGroupId(groupId);
		return ((GetGroupMemberListRequest.Response) this.request(request)).getData();
	}


	public String getCookies(String domain) {
		GetCookiesHttpRequest request = new GetCookiesHttpRequest();
		request.setDomain(domain);
		return ((GetCookiesRequest.Response) this.request(request)).getData().getCookies();
	}


	public int getCrsfToken() {
		return ((GetCrsfTokenRequest.Response) this.request(new GetCrsfTokenHttpRequest())).getData().getToken();
	}


	public GetCredentialsRequest.ResponseData getCredentials(String domain) {
		return ((GetCredentialsRequest.Response) this.request(new GetCredentialsHttpRequest())).getData();
	}


	public String getRecord(String file, String outFormat, boolean fullPath) {
		GetRecordHttpRequest request = new GetRecordHttpRequest();
		request.setFile(file);
		request.setFullPath(fullPath);
		request.setOutFormat(outFormat);
		return ((GetRecordRequest.Response) this.request(request)).getData().getFile();
	}


	public GetImageRequest.Response getImage(String file) {
		GetImageHttpRequest request = new GetImageHttpRequest();
		request.setFile(file);
		return (GetImageRequest.Response) this.request(request);
	}


	public boolean canSendImage() {
		return ((CanSendImageRequest.Response) this.request(new CanSendImageHttpRequest())).getData().isYes();
	}


	public boolean canSendRecord() {
		return ((CanSendRecordRequest.Response) this.request(new CanSendRecordHttpRequest())).getData().isYes();
	}


	public List<_GetFriendListRequset.ResponseData> _getFriendList() {
		_GetFriendListHttpRequest request = new _GetFriendListHttpRequest();
		request.setFlat(false);
		return ((_GetFriendListRequset.Response) this.request(request)).getData();
	}


	public FlatResponseData _getFlatFriendList() {
		_GetFriendListHttpRequest request = new _GetFriendListHttpRequest();
		request.setFlat(true);
		return ((_GetFriendListRequset.FlatResponse) this.request(request)).getData();
	}


	public _GetGroupInfoRequest.ResponseData _getGroupInfo(long groupId) {
		_GetGroupInfoHttpRequest request = new _GetGroupInfoHttpRequest();
		request.setGroupId(groupId);
		return ((_GetGroupInfoRequest.Response) this.request(request)).getData();
	}


	public _GetVipInfoRequest.ResponseData _getVipInfo(long userId) {
		_GetVipInfoHttpRequest request = new _GetVipInfoHttpRequest();
		request.setUserId(userId);
		return ((_GetVipInfoRequest.Response) request(request)).getData();
	}


	public List<_GetGroupNoticeRequest.ResponseData> _getGroupNotice(long groupId) {
		_GetGroupNoticeHttpRequest request = new _GetGroupNoticeHttpRequest();
		request.setGroupId(groupId);
		return ((_GetGroupNoticeRequest.Response) request(request)).getData();
	}


	public _SendGroupNoticeRequest.Response _sendGroupNotice(long groupId, String title, String content) {
		_SendGroupNoticeHttpRequest request = new _SendGroupNoticeHttpRequest();
		request.setContent(content);
		request.setGroupId(groupId);
		request.setTitle(title);
		return (_SendGroupNoticeRequest.Response) this.send0(request);
	}

	/**
	 * 传递request的key
	 */
	private static final AttributeKey<CoolQHttpRequest> REQUEST_KEY = AttributeKey.newInstance("key");

	public void requestAsync(CoolQHttpRequest request) {
		requestQueue.offer(request);
		connect();
	}

	public Response<?> request(CoolQHttpRequest request) {
		long start = System.currentTimeMillis();
		requestAsync(request);
		while (request.getResponse() == null) {
			try {
				if (System.currentTimeMillis() - start < maxRespondTime) {
					Thread.sleep(1);
				} else {
					throw new RespondTimeOutExcption("请求" + request.uri() + "响应超时");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return request.getResponse();
	}

	private class RobotHttpClientPipeline extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) {
			ChannelPipeline p = ch.pipeline();
			p.addLast(new HttpClientCodec());
			p.addLast(new HttpObjectAggregator(1 << 22));
			p.addLast(new RobotHttpClientHandler());
		}
	}

	private class RobotHttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

		@Override
		public void channelActive(ChannelHandlerContext ctx) {
			CoolQHttpRequest request = requestQueue.poll();
			Channel ch = ctx.channel();
			ch.attr(REQUEST_KEY).set(request);
			assert request != null;
			FullHttpRequest fullHttpRequest = request.getRequest();
			ch.writeAndFlush(fullHttpRequest);
			logger.debug("发送请求到cqhttp api: {}", request.uri());
		}

		@Override
		public void channelRead0(ChannelHandlerContext ctx, FullHttpResponse res) {
			Channel ch = ctx.channel();
			CoolQHttpRequest request = ch.attr(REQUEST_KEY).getAndSet(null);
			String json = res.content().toString(CharsetUtil.UTF_8);
			CoolQRequest.Response<?> response = JsonUtil.toBean(json, request.getResponseClass());
			request.setResponse(response);
			assert response != null;
			int retcode = response.getRetcode();
			if (retcode != 0) {
				switch (retcode) {
				case 1:
					logger.warn("请求已进入异步执行，返回结果未知。");
					logger.warn("retcode: {}, status: {}", retcode, response.getStatus());
					break;
				case 100:
					logger.error("请求失败，参数缺失或者参数无效。");
					logger.error("retcode: {}, status: {}", retcode, response.getStatus());
					break;
				case 102:
					logger.error("请求失败，参数有效但是无法操作；一般是因为传入参数有效但没有权限，比如试图获取没有加入的群组的成员列表。");
					logger.error("retcode: {}, status: {}", retcode, response.getStatus());
					break;
				case 103:
					logger.error("操作失败，用户权限不足或者文件系统异常");
					logger.error("retcode: {}, status: {}", retcode, response.getStatus());
					break;
				case 104:
					logger.error("coolq提供的凭证失效，请尝试清除coolq缓存");
					break;
				case 201:
					logger.error("工作线程未正确初始化，无法执行异步任务");
					logger.error("retcode: {}, status: {}", retcode, response.getStatus());
					break;
				}
			}
			logger.debug("{}请求完成，响应为: {}", request.uri(), response);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
		}
	}

}
