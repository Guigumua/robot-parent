package com.github.guigumua.robot.common.client;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.commom.event.EventType;
import com.github.guigumua.robot.commom.event.bean.message.DiscussMessageEvent;
import com.github.guigumua.robot.commom.event.bean.message.GroupMessageEvent;
import com.github.guigumua.robot.commom.event.bean.message.MessageEvent;
import com.github.guigumua.robot.commom.event.bean.notice.FriendAddNoticeEvent;
import com.github.guigumua.robot.commom.event.bean.request.GroupAddInviteRequestEvent;
import com.github.guigumua.robot.commom.request.CoolQHttpRequest;
import com.github.guigumua.robot.commom.request.CoolQHttpRequest.Response;
import com.github.guigumua.robot.commom.request.bean.CanSendImageRequest;
import com.github.guigumua.robot.commom.request.bean.CanSendRecordRequest;
import com.github.guigumua.robot.commom.request.bean.DeleteMsgRequest;
import com.github.guigumua.robot.commom.request.bean.GetCookiesRequest;
import com.github.guigumua.robot.commom.request.bean.GetCredentialsRequest;
import com.github.guigumua.robot.commom.request.bean.GetCrsfTokenRequest;
import com.github.guigumua.robot.commom.request.bean.GetFriendListRequest;
import com.github.guigumua.robot.commom.request.bean.GetGroupInfoRequest;
import com.github.guigumua.robot.commom.request.bean.GetGroupListRequest;
import com.github.guigumua.robot.commom.request.bean.GetGroupMemberInfoRequest;
import com.github.guigumua.robot.commom.request.bean.GetGroupMemberListRequest;
import com.github.guigumua.robot.commom.request.bean.GetImageRequest;
import com.github.guigumua.robot.commom.request.bean.GetLoginInfoRequest;
import com.github.guigumua.robot.commom.request.bean.GetRecordRequest;
import com.github.guigumua.robot.commom.request.bean.GetStrangerInfoRequest;
import com.github.guigumua.robot.commom.request.bean.SendLikeRequest;
import com.github.guigumua.robot.commom.request.bean.SendMsgRequest;
import com.github.guigumua.robot.commom.request.bean.SetDiscussLeaveRequest;
import com.github.guigumua.robot.commom.request.bean.SetFriendAddRequestRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupAddRequestRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupAdminRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupAnonymousBanRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupAnonymousRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupBanRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupCardRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupKickRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupLeaveRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupSpecialTitleRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupWholeBanRequest;
import com.github.guigumua.robot.commom.request.bean._GetFriendListRequset;
import com.github.guigumua.robot.commom.request.bean._GetGroupInfoRequest;
import com.github.guigumua.robot.commom.request.bean._GetGroupNoticeRequest;
import com.github.guigumua.robot.commom.request.bean._GetVipInfoRequest;
import com.github.guigumua.robot.commom.request.bean._SendGroupNoticeRequest;
import com.github.guigumua.robot.commom.request.bean.SetGroupAnonymousBanRequest.Anonymous;
import com.github.guigumua.robot.commom.request.bean._GetFriendListRequset.FlatResponseData;
import com.github.guigumua.robot.commom.util.JsonUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

public class RobotClient {
	public static final RobotClient PLACEHOLDER_CLIENT = new RobotClient();
	private Logger logger = LoggerFactory.getLogger(RobotClient.class);
	private static final NioEventLoopGroup GROUP = new NioEventLoopGroup();
	private Bootstrap bootstrap = new Bootstrap();
	private RobotClientPipeline pipeline = new RobotClientPipeline();
	private String host;
	private int port;
	/**
	 * 异步请求开关
	 */
	private boolean async = true;
//	private String secret;
	/**
	 * 请求任务队列
	 */
	private final ConcurrentLinkedQueue<CoolQHttpRequest> requestQueue = new ConcurrentLinkedQueue<>();

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public boolean isAsync() {
		return async;
	}

	/**
	 * 设置是否异步执行
	 * 
	 * @param async 是否异步
	 */
	public void setAsync(boolean async) {
		this.async = async;
	}

	private RobotClient() {
	}

	public RobotClient(String host, int port) {
		bootstrap.group(GROUP).option(ChannelOption.SO_KEEPALIVE, true).channel(NioSocketChannel.class)
				.handler(pipeline);
		this.host = host;
		this.port = port;
	}

	private int connectTimes = 0;

	private void connect() {
		if (connectTimes++ > 5) {
			logger.info("连接重试超时");
			requestQueue.poll();
		} else {
			listenConnect(bootstrap.connect(host, port));
		}
	}

	private void listenConnect(ChannelFuture channelFuture) {
		channelFuture.addListener((ChannelFuture f) -> {
			if (channelFuture.isSuccess()) {
				connectTimes = 0;
			} else {
				logger.info("连接失败");
				GROUP.schedule(() -> {
					connect();
				}, (1 << connectTimes) * 5, TimeUnit.SECONDS);
			}
		});
	}

	private CoolQHttpRequest.Response<?> send0(CoolQHttpRequest request) {
		if (async) {
			this.requestAsync(request);
		} else {
			this.request(request);
		}
		return request.getResponse();
	}

	// 下面的方法默认异步
	/**
	 * 发送消息，默认异步执行
	 * 
	 * @param request coolq API接收的请求格式的封装对象
	 * @return 发送结果响应，异步执行时响应为null
	 */
	public SendMsgRequest.Response send(SendMsgRequest request) {
		return ((SendMsgRequest.Response) send0(request));
	}

	/**
	 * 根据event自动发送
	 * 
	 * @param e
	 * @param message
	 * @return
	 */
	public SendMsgRequest.Response send(MessageEvent e, String message) {
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

	/**
	 * 发送私聊消息，默认异步执行
	 * 
	 * @param userId  接收者qq号
	 * @param message 消息内容
	 * @return 发送结果响应，异步执行时响应为null
	 */
	public SendMsgRequest.Response sendPrivateMsg(long userId, String message) {
		SendMsgRequest request = new SendMsgRequest();
		request.setMessageType(SendMsgRequest.PRIVATE_TYPE);
		request.setUserId(userId);
		request.setMessage(message);
		return (com.github.guigumua.robot.commom.request.bean.SendMsgRequest.Response) send0(request);
	}

	/**
	 * 发送群聊消息，默认异步
	 * 
	 * @param groupId 群号
	 * @param message 消息内容
	 * @return 发送消息响应，默认为null
	 */
	public SendMsgRequest.Response sendGroupMsg(long groupId, String message) {
		SendMsgRequest request = new SendMsgRequest();
		request.setMessageType(SendMsgRequest.GROUP_TYPE);
		request.setGroupId(groupId);
		request.setMessage(message);
		return (com.github.guigumua.robot.commom.request.bean.SendMsgRequest.Response) send0(request);
	}

	/**
	 * 发送消息并撤回
	 * 
	 * @param groupId 群号
	 * @param message 消息
	 * @param delay   发送后多久撤回
	 * @param unit    撤回消息的时间单位
	 */
	public void sendGroupAndDelete(long groupId, String message, int delay, TimeUnit unit) {
		SendMsgRequest request = new SendMsgRequest();
		request.setMessageType(SendMsgRequest.GROUP_TYPE);
		request.setGroupId(groupId);
		request.setMessage(message);
		SendMsgRequest.Response response = (SendMsgRequest.Response) this.request(request);
		SendMsgRequest.ResponseData data = response.getData();
		int messageId = data.getMessageId();
		GROUP.schedule(() -> {
			deleteMsg(messageId);
		}, delay, unit);
	}

	/**
	 * 发送讨论组消息，默认异步
	 * 
	 * @param discussId 讨论组id
	 * @param message   消息内容
	 * @return 发送消息结果响应，异步执行为null
	 */
	public SendMsgRequest.Response sendDiscussMsg(long discussId, String message) {
		SendMsgRequest request = new SendMsgRequest();
		request.setMessageType(SendMsgRequest.DISCUSS_TYPE);
		request.setGroupId(discussId);
		request.setMessage(message);
		this.requestAsync(request);
		return (com.github.guigumua.robot.commom.request.bean.SendMsgRequest.Response) send0(request);
	}

	/**
	 * 发送赞，默认异步
	 * 
	 * @param userId 接收赞的用户qq
	 * @param times  赞多少次，最大10
	 * @return 发送结果响应，默认为null
	 */
	public SendLikeRequest.Response sendLike(long userId, int times) {
		SendLikeRequest request = new SendLikeRequest();
		request.setUserId(userId);
		request.setTimes(times);
		return (com.github.guigumua.robot.commom.request.bean.SendLikeRequest.Response) send0(request);
	}

	/**
	 * 撤回消息，默认异步
	 * 
	 * @param messageId 消息的id
	 * @return 撤回消息结果的响应，异步执行时为null
	 */
	public DeleteMsgRequest.Response deleteMsg(int messageId) {
		DeleteMsgRequest request = new DeleteMsgRequest();
		request.setMessageId(messageId);
		return (com.github.guigumua.robot.commom.request.bean.DeleteMsgRequest.Response) send0(request);
	}

	/**
	 * 踢人，默认不拒绝请求
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public SetGroupKickRequest.Response groupKick(long groupId, long userId) {
		return setGroupKick(groupId, userId, false);
	}

	/**
	 * 踢人
	 * 
	 * @param groupId 群号
	 * @param userId  要踢的人的qq
	 * @param reject  是否拒绝再次请求
	 * @return
	 */
	public SetGroupKickRequest.Response setGroupKick(long groupId, long userId, boolean reject) {
		SetGroupKickRequest request = new SetGroupKickRequest();
		request.setGroupId(groupId);
		request.setUserId(userId);
		request.setRejectAddRequest(reject);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupKickRequest.Response) send0(request);
	}

	/**
	 * 禁言
	 * 
	 * @param groupId  群号
	 * @param userId   要禁言的人的qq
	 * @param duration 禁言时间，0表示解除
	 * @return
	 */
	public SetGroupBanRequest.Response setGroupBan(long groupId, long userId, int duration) {
		SetGroupBanRequest request = new SetGroupBanRequest();
		request.setDuration(duration);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupBanRequest.Response) send0(request);
	}

	/**
	 * 群匿名禁言
	 * 
	 * @param groupId   群号
	 * @param anonymous 匿名者（上报事件中获取）
	 * @param flag      禁言用户的flag（上报事件中获取）
	 * @param duration  禁言时间，0解除
	 * @return
	 */
	public SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, Anonymous anonymous, String flag,
			int duration) {
		SetGroupAnonymousBanRequest request = new SetGroupAnonymousBanRequest();
		request.setAnonymous(anonymous);
		request.setDuration(duration);
		request.setFlag(flag);
		request.setGroupId(groupId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupAnonymousBanRequest.Response) send0(request);
	}

	/**
	 * 群匿名禁言
	 * 
	 * @param groupId  群号
	 * @param e        群消息事件
	 * @param duration 禁言时间，0解除
	 * @return
	 */
	public SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, GroupMessageEvent e, int duration) {
		SetGroupAnonymousBanRequest request = new SetGroupAnonymousBanRequest();
		request.setAnonymous(e.getAnonymous());
		request.setDuration(duration);
		request.setFlag(e.getAnonymous().getFlag());
		request.setGroupId(groupId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupAnonymousBanRequest.Response) send0(request);
	}

	/**
	 * 群全员禁言
	 * 
	 * @param groupId 群号
	 * @param enable  是否全员禁言
	 * @return
	 */
	public SetGroupWholeBanRequest.Response setGroupWholeBan(long groupId, boolean enable) {
		SetGroupWholeBanRequest request = new SetGroupWholeBanRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupWholeBanRequest.Response) send0(request);
	}

	/**
	 * 设置管理员
	 * 
	 * @param groupId 群号
	 * @param userId  qq号
	 * @param enable  true设置，false取消
	 * @return
	 */
	public SetGroupAdminRequest.Response setGroupAdmin(long groupId, long userId, boolean enable) {
		SetGroupAdminRequest request = new SetGroupAdminRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupAdminRequest.Response) send0(request);
	}

	/**
	 * 开启群匿名
	 * 
	 * @param groupId 群号
	 * @param enable  开启or关闭
	 * @return
	 */
	public SetGroupAnonymousRequest.Response setGroupAnonymous(long groupId, boolean enable) {
		SetGroupAnonymousRequest request = new SetGroupAnonymousRequest();
		request.setEnable(enable);
		request.setGroupId(groupId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupAnonymousRequest.Response) send0(request);
	}

	/**
	 * 群名片设置
	 * 
	 * @param groupId
	 * @param userId
	 * @param card
	 * @return
	 */
	public SetGroupCardRequest.Response setGroupCard(long groupId, long userId, String card) {
		SetGroupCardRequest request = new SetGroupCardRequest();
		request.setCard(card);
		request.setGroupId(groupId);
		request.setUserId(userId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupCardRequest.Response) send0(request);
	}

	/**
	 * 退出群
	 * 
	 * @param groupId   群号
	 * @param isDismiss 如果是群主，是否解散群
	 * @return
	 */
	public SetGroupLeaveRequest.Response setGroupLeave(long groupId, boolean isDismiss) {
		SetGroupLeaveRequest request = new SetGroupLeaveRequest();
		request.setDismiss(isDismiss);
		request.setGroupId(groupId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupLeaveRequest.Response) send0(request);
	}

	/**
	 * 设置专属头衔
	 * 
	 * @param groupId
	 * @param userId
	 * @param specialTitle 头衔
	 * @return
	 */
	public SetGroupSpecialTitleRequest.Response setGroupSpecialTitle(long groupId, long userId, String specialTitle) {
		SetGroupSpecialTitleRequest request = new SetGroupSpecialTitleRequest();
		request.setDuration(-1);
		request.setGroupId(groupId);
		request.setSpecialTitle(specialTitle);
		request.setUserId(userId);
		return (com.github.guigumua.robot.commom.request.bean.SetGroupSpecialTitleRequest.Response) send0(request);
	}

	/**
	 * 退出讨论组
	 * 
	 * @param discussId
	 * @return
	 */
	public SetDiscussLeaveRequest.Response setDiscussLeave(long discussId) {
		SetDiscussLeaveRequest request = new SetDiscussLeaveRequest();
		request.setDiscussId(discussId);
		return (com.github.guigumua.robot.commom.request.bean.SetDiscussLeaveRequest.Response) send0(request);
	}

	/**
	 * 处理加好友请求
	 * 
	 * @param flag    上报事件中获取的flag
	 * @param approve 是否同意
	 * @param remark  备注，默认空
	 * @return
	 */
	public SetFriendAddRequestRequest.Response setFriendAddRequest(String flag, boolean approve, String remark) {
		SetFriendAddRequestRequest request = new SetFriendAddRequestRequest();
		request.setApprove(approve);
		request.setFlag(flag);
		request.setRemark(remark);
		return (com.github.guigumua.robot.commom.request.bean.SetFriendAddRequestRequest.Response) send0(request);
	}

	/**
	 * 处理加好友请求
	 * 
	 * @param e       好友请求事件
	 * @param approve 是否同意
	 * @param remark  备注
	 * @return
	 */
	public SetFriendAddRequestRequest.Response setFriendAddRequest(FriendAddNoticeEvent e, boolean approve,
			String remark) {
		SetFriendAddRequestRequest request = new SetFriendAddRequestRequest();
		request.setApprove(approve);
		request.setFlag(e.getFlag());
		request.setRemark(remark);
		return (com.github.guigumua.robot.commom.request.bean.SetFriendAddRequestRequest.Response) send0(request);
	}

	/**
	 * 处理加群请求
	 * 
	 * @param e       加群请求事件
	 * @param approve 同意
	 * @param reason  如果拒绝，拒绝原因
	 * @return
	 */
	public SetGroupAddRequestRequest.Response setGroupAddRequest(GroupAddInviteRequestEvent e, boolean approve,
			String reason) {
		SetGroupAddRequestRequest request = new SetGroupAddRequestRequest();
		request.setApprove(approve);
		request.setFlag(e.getFlag());
		request.setReason(reason);
		request.setType(e.getSubType());
		return (com.github.guigumua.robot.commom.request.bean.SetGroupAddRequestRequest.Response) send0(request);
	}

	// 上面的方法默认异步

	// 下面的方法只能同步
	/**
	 * 获取登录号信息
	 * 
	 * @return 登录号信息响应
	 */
	public GetLoginInfoRequest.ResponseData getLoginInfo() {
		return ((GetLoginInfoRequest.Response) request(new GetLoginInfoRequest())).getData();
	}

	/**
	 * 获取陌生人信息
	 * 
	 * @param userId
	 * @return
	 */
	public GetStrangerInfoRequest.ResponseData getStrangerInfo(long userId) {
		GetStrangerInfoRequest request = new GetStrangerInfoRequest();
		request.setUserId(userId);
		return ((GetStrangerInfoRequest.Response) this.request(request)).getData();
	}

	/**
	 * 获取好友列表
	 * 
	 * @return
	 */
	public List<GetFriendListRequest.ResponseData> getFriendList() {
		return ((GetFriendListRequest.Response) this.request(new GetFriendListRequest())).getData();
	}

	/**
	 * 获取群列表
	 * 
	 * @return
	 */
	public List<GetGroupListRequest.ResponseData> getGroupList() {
		return ((GetGroupListRequest.Response) this.request(new GetGroupListRequest())).getData();
	}

	/**
	 * 获取群信息
	 * 
	 * @param groupId
	 * @return
	 */
	public GetGroupInfoRequest.ResponseData getGroupInfo(long groupId) {
		GetGroupInfoRequest request = new GetGroupInfoRequest();
		request.setGroupId(groupId);
		return ((GetGroupInfoRequest.Response) this.request(request)).getData();
	}

	/**
	 * 获取群成员信息
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public GetGroupMemberInfoRequest.ResponseData getGroupMemberInfo(long groupId, long userId) {
		GetGroupMemberInfoRequest request = new GetGroupMemberInfoRequest();
		request.setGroupId(groupId);
		request.setUserId(userId);
		return ((GetGroupMemberInfoRequest.Response) this.request(request)).getData();
	}

	/**
	 * 获取群成员列表
	 * 
	 * @param groupId
	 * @return
	 */
	public List<GetGroupMemberInfoRequest.ResponseData> getGroupMemberList(long groupId) {
		GetGroupMemberListRequest request = new GetGroupMemberListRequest();
		request.setGroupId(groupId);
		return ((GetGroupMemberListRequest.Response) this.request(request)).getData();
	}

	/**
	 * 获取指定域名cookies
	 * 
	 * @param domain
	 * @return
	 */
	public String getCookies(String domain) {
		GetCookiesRequest request = new GetCookiesRequest();
		request.setDomain(domain);
		return ((com.github.guigumua.robot.commom.request.bean.GetCookiesRequest.Response) this.request(request)).getData().getCookies();
	}

	/**
	 * 获取crsf token
	 * 
	 * @return
	 */
	public int getCrsfToken() {
		return ((com.github.guigumua.robot.commom.request.bean.GetCrsfTokenRequest.Response) this.request(new GetCrsfTokenRequest()))
				.getData().getToken();
	}

	/**
	 * 获取cookies和crsf token的联合接口
	 * 
	 * @param domain
	 * @return
	 */
	public GetCredentialsRequest.ResponseData getCredentials(String domain) {
		return ((GetCredentialsRequest.Response) this.request(new GetCredentialsRequest())).getData();
	}

	/**
	 * 转换语音格式
	 * 
	 * @param file      收到的语音文件名
	 * @param outFormat 输出格式 mp3、amr、wma、m4a、spx、ogg、wav、flac
	 * @param fullPath  输出全路径？
	 * @return 文件路径
	 */
	public String getRecord(String file, String outFormat, boolean fullPath) {
		GetRecordRequest request = new GetRecordRequest();
		request.setFile(file);
		request.setFullPath(fullPath);
		request.setOutFormat(outFormat);
		return ((com.github.guigumua.robot.commom.request.bean.GetRecordRequest.Response) this.request(request)).getData().getFile();
	}

	/**
	 * 下载图片
	 * 
	 * @param file
	 * @return
	 */
	public GetImageRequest.Response getImage(String file) {
		GetImageRequest request = new GetImageRequest();
		request.setFile(file);
		return (com.github.guigumua.robot.commom.request.bean.GetImageRequest.Response) this.request(request);
	}

	/**
	 * 查看是否可以发送图片
	 * 
	 * @return
	 */
	public boolean canSendImage() {
		return ((CanSendImageRequest.Response) this.request(new CanSendImageRequest())).getData().isYes();
	}

	/**
	 * 检查可以发送语音
	 * 
	 * @return
	 */
	public boolean canSendRecord() {
		return ((CanSendRecordRequest.Response) this.request(new CanSendRecordRequest())).getData().isYes();
	}

	/**
	 * 试验性api 获取好友列表
	 * 
	 * @return
	 */
	public List<_GetFriendListRequset.ResponseData> _getFriendList() {
		_GetFriendListRequset request = new _GetFriendListRequset();
		request.setFlat(false);
		return ((_GetFriendListRequset.Response) this.request(request)).getData();
	}

	/**
	 * 试验性api 获取扁平化好友列表
	 * 
	 * @return
	 */
	public FlatResponseData _getFlatFriendList() {
		_GetFriendListRequset request = new _GetFriendListRequset();
		request.setFlat(true);
		return ((_GetFriendListRequset.FlatResponse) this.request(request)).getData();
	}

	/**
	 * 试验性api 获取群信息
	 * 
	 * @param groupId
	 * @return
	 */
	public _GetGroupInfoRequest.ResponseData _getGroupInfo(long groupId) {
		_GetGroupInfoRequest request = new _GetGroupInfoRequest();
		request.setGroupId(groupId);
		return ((_GetGroupInfoRequest.Response) this.request(request)).getData();
	}

	/**
	 * 试验性api 获取比较详细的qq号信息
	 * 
	 * @param userId
	 * @return
	 */
	public _GetVipInfoRequest.ResponseData _getVipInfo(long userId) {
		_GetVipInfoRequest request = new _GetVipInfoRequest();
		request.setUserId(userId);
		return ((_GetVipInfoRequest.Response) request(request)).getData();
	}

	/**
	 * 试验性api 获取群公告列表
	 * 
	 * @param groupId
	 * @return
	 */
	public List<_GetGroupNoticeRequest.ResponseData> _getGroupNotice(long groupId) {
		_GetGroupNoticeRequest request = new _GetGroupNoticeRequest();
		request.setGroupId(groupId);
		return ((_GetGroupNoticeRequest.Response) request(request)).getData();
	}

	/**
	 * 试验性api 发送群公告 默认异步
	 * 
	 * @param groupId 群
	 * @param title   公告标题
	 * @param content 公告内容
	 * @return
	 */
	public _SendGroupNoticeRequest.Response _sendGroupNotice(long groupId, String title, String content) {
		_SendGroupNoticeRequest request = new _SendGroupNoticeRequest();
		request.setContent(content);
		request.setGroupId(groupId);
		request.setTitle(title);
		return (_SendGroupNoticeRequest.Response) this.send0(request);
	}

	/**
	 * 传递request的key
	 */
	private static final AttributeKey<CoolQHttpRequest> REQUEST_KEY = AttributeKey.newInstance("key");

	/**
	 * 核心异步api
	 * 
	 * @param request
	 */

	public void requestAsync(CoolQHttpRequest request) {
		requestQueue.offer(request);
		connect();
	}

	/**
	 * 核心同步api
	 * 
	 * @param request
	 * @return
	 */
	public Response<?> request(CoolQHttpRequest request) {
		requestAsync(request);
		for (; request.getResponse() == null;) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return request.getResponse();
	}

	private class RobotClientPipeline extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline p = ch.pipeline();
//			p.addLast(new LoggingHandler(LogLevel.INFO));
			p.addLast(new HttpClientCodec());
			p.addLast(new HttpObjectAggregator(1 << 22));
			p.addLast(new RobotClientHandler());
		}
	}

	private class RobotClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			CoolQHttpRequest request = requestQueue.poll();
			Channel ch = ctx.channel();
			ch.attr(REQUEST_KEY).set(request);
			FullHttpRequest fullHttpRequest = request.get();
			ch.writeAndFlush(fullHttpRequest);
			logger.debug("write and flush request completed: {}", fullHttpRequest);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object res) throws Exception {
			FullHttpResponse msg = (FullHttpResponse) res;
			Channel ch = ctx.channel();
			CoolQHttpRequest request = ch.attr(REQUEST_KEY).getAndSet(null);
			String json = msg.content().toString(CharsetUtil.UTF_8);
			Response<?> response = JsonUtil.toBean(json, request.getResponseClass());
			request.setResponse(response);
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
			logger.debug("read response completed: {}", response);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
		}

	}
}
