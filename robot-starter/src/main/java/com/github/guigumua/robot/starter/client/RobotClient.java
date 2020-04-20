package com.github.guigumua.robot.starter.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.guigumua.robot.common.event.message.GroupMessageEvent;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.event.notice.FriendAddNoticeEvent;
import com.github.guigumua.robot.common.event.request.GroupAddInviteRequestEvent;
import com.github.guigumua.robot.common.request.DeleteMsgRequest;
import com.github.guigumua.robot.common.request.GetCredentialsRequest;
import com.github.guigumua.robot.common.request.GetFriendListRequest;
import com.github.guigumua.robot.common.request.GetGroupInfoRequest;
import com.github.guigumua.robot.common.request.GetGroupListRequest;
import com.github.guigumua.robot.common.request.GetGroupMemberInfoRequest;
import com.github.guigumua.robot.common.request.GetImageRequest;
import com.github.guigumua.robot.common.request.GetLoginInfoRequest;
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
import com.github.guigumua.robot.common.request.http.SendMsgHttpRequest;

public interface RobotClient {

    boolean isUseWs();

    String getHost();

    int getPort();

    boolean isAsync();

    void setAsync(boolean async);

    long getSelfId();

    void setSelfId(long userId);


    /**
     * @param e       事件
     * @param message 消息
     * @return 根据事件类型自动发送消息
     */
    SendMsgRequest.Response sendMsg(MessageEvent e, String message);

    /**
     * 发送私聊消息，默认异步执行
     *
     * @param userId  接收者qq号
     * @param message 消息内容
     * @return 发送结果响应，异步执行时响应为null
     */
    SendMsgRequest.Response sendPrivateMsg(long userId, String message);

    /**
     * 发送群聊消息，默认异步
     *
     * @param groupId 群号
     * @param message 消息内容
     * @return 发送消息响应，默认为null
     */
    SendMsgRequest.Response sendGroupMsg(long groupId, String message);

    /**
     * 发送消息并撤回
     *
     * @param groupId 群号
     * @param message 消息
     * @param delay   发送后多久撤回
     * @param unit    撤回消息的时间单位
     */
    void sendGroupAndDelete(long groupId, String message, int delay, TimeUnit unit);

    /**
     * 发送讨论组消息，默认异步
     *
     * @param discussId 讨论组id
     * @param message   消息内容
     * @return 发送消息结果响应，异步执行为null
     */
    SendMsgRequest.Response sendDiscussMsg(long discussId, String message);

    /**
     * 发送赞，默认异步
     *
     * @param userId 接收赞的用户qq
     * @param times  赞多少次，最大10
     * @return 发送结果响应，默认为null
     */
    SendLikeRequest.Response sendLike(long userId, int times);

    /**
     * 撤回消息，默认异步
     *
     * @param messageId 消息的id
     * @return 撤回消息结果的响应，异步执行时为null
     */
    DeleteMsgRequest.Response deleteMsg(int messageId);

    /**
     * 踢人，默认不拒绝请求
     *
     * @param groupId 群号
     * @param userId  用户qq
     * @return 异步为null
     */
    SetGroupKickRequest.Response groupKick(long groupId, long userId);

    /**
     * 踢人
     *
     * @param groupId 群号
     * @param userId  要踢的人的qq
     * @param reject  是否拒绝再次请求
     * @return 异步为null
     */
    SetGroupKickRequest.Response setGroupKick(long groupId, long userId, boolean reject);

    /**
     * 禁言
     *
     * @param groupId  群号
     * @param userId   要禁言的人的qq
     * @param duration 禁言时间，0表示解除
     * @return 异步为null
     */
    SetGroupBanRequest.Response setGroupBan(long groupId, long userId, int duration);

    /**
     * 群匿名禁言
     *
     * @param groupId   群号
     * @param anonymous 匿名者（上报事件中获取）
     * @param flag      禁言用户的flag（上报事件中获取）
     * @param duration  禁言时间，0解除
     * @return 异步为null
     */
    SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, Anonymous anonymous, String flag,
                                                              int duration);

    /**
     * 群匿名禁言
     *
     * @param groupId  群号
     * @param e        群消息事件
     * @param duration 禁言时间，0解除
     * @return 异步为null
     */
    SetGroupAnonymousBanRequest.Response setGroupAnonymousBan(long groupId, GroupMessageEvent e, int duration);

    /**
     * 群全员禁言
     *
     * @param groupId 群号
     * @param enable  是否全员禁言
     * @return 异步为null
     */
    SetGroupWholeBanRequest.Response setGroupWholeBan(long groupId, boolean enable);

    /**
     * 设置管理员
     *
     * @param groupId 群号
     * @param userId  qq号
     * @param enable  true设置，false取消
     * @return 异步为null
     */
    SetGroupAdminRequest.Response setGroupAdmin(long groupId, long userId, boolean enable);

    /**
     * 开启群匿名
     *
     * @param groupId 群号
     * @param enable  开启or关闭
     * @return 异步为null
     */
    SetGroupAnonymousRequest.Response setGroupAnonymous(long groupId, boolean enable);

    /**
     * 群名片设置
     *
     * @param groupId
     * @param userId
     * @param card
     * @return 异步为null
     */
    SetGroupCardRequest.Response setGroupCard(long groupId, long userId, String card);

    /**
     * 退出群
     *
     * @param groupId   群号
     * @param isDismiss 如果是群主，是否解散群
     * @return 异步为null
     */
    SetGroupLeaveRequest.Response setGroupLeave(long groupId, boolean isDismiss);

    /**
     * 设置专属头衔
     *
     * @param groupId
     * @param userId
     * @param specialTitle 头衔
     * @return 异步为null
     */
    SetGroupSpecialTitleRequest.Response setGroupSpecialTitle(long groupId, long userId, String specialTitle);

    /**
     * 退出讨论组
     *
     * @param discussId 讨论组号
     * @return 异步为null
     */
    SetDiscussLeaveRequest.Response setDiscussLeave(long discussId);

    /**
     * 处理加好友请求
     *
     * @param flag    上报事件中获取的flag
     * @param approve 是否同意
     * @param remark  备注，默认空
     * @return 异步为null
     */
    SetFriendAddRequestRequest.Response setFriendAddRequest(String flag, boolean approve, String remark);

    /**
     * 处理加好友请求
     *
     * @param e       好友请求事件
     * @param approve 是否同意
     * @param remark  备注
     * @return 异步为null
     */
    SetFriendAddRequestRequest.Response setFriendAddRequest(FriendAddNoticeEvent e, boolean approve, String remark);

    /**
     * 处理加群请求
     *
     * @param e       加群请求事件
     * @param approve 同意
     * @param reason  如果拒绝，拒绝原因
     * @return 异步为null
     */
    SetGroupAddRequestRequest.Response setGroupAddRequest(GroupAddInviteRequestEvent e, boolean approve, String reason);

    // 上面的方法默认异步

    // 下面的方法只能同步

    /**
     * 获取登录号信息
     *
     * @return 登录号信息响应
     */
    GetLoginInfoRequest.ResponseData getLoginInfo();

    /**
     * 获取陌生人信息
     *
     * @param userId qq
     * @return 陌生人信息
     */
    GetStrangerInfoRequest.ResponseData getStrangerInfo(long userId);

    /**
     * 获取好友列表
     *
     * @return 好友列表
     */
    List<GetFriendListRequest.ResponseData> getFriendList();

    /**
     * 获取群列表
     *
     * @return 群列表
     */
    List<GetGroupListRequest.ResponseData> getGroupList();

    /**
     * 获取群信息
     *
     * @param groupId 群号
     * @return 群信息
     */
    GetGroupInfoRequest.ResponseData getGroupInfo(long groupId);

    /**
     * 获取群成员信息
     *
     * @param groupId 群号
     * @param userId QQ号
     * @return 群成员信息
     */
    GetGroupMemberInfoRequest.ResponseData getGroupMemberInfo(long groupId, long userId);

    /**
     * 获取群成员列表
     *
     * @param groupId 群号
     * @return 群成员列表
     */
    List<GetGroupMemberInfoRequest.ResponseData> getGroupMemberList(long groupId);

    /**
     * 获取指定域名cookies
     *
     * @param domain 域名
     * @return cookies coolq提供的coolq存在一定问题
     */
    String getCookies(String domain);

    /**
     * 获取crsf token
     *
     * @return  token
     */
    int getCrsfToken();

    /**
     * 获取cookies和crsf token的联合接口
     *
     * @param domain 域名
     * @return 联合对象
     */
    GetCredentialsRequest.ResponseData getCredentials(String domain);

    /**
     * 转换语音格式
     *
     * @param file      收到的语音文件名
     * @param outFormat 输出格式 mp3、amr、wma、m4a、spx、ogg、wav、flac
     * @param fullPath  输出全路径？
     * @return 文件路径
     */
    String getRecord(String file, String outFormat, boolean fullPath);

    /**
     * 下载图片
     *
     * @param file 文件位置
     * @return 图片路径
     */
    GetImageRequest.Response getImage(String file);

    /**
     * 查看是否可以发送图片
     *
     * @return 是否可以发送图片
     */
    boolean canSendImage();

    /**
     * 检查可以发送语音
     *
     * @return 是否可以发送语音
     */
    boolean canSendRecord();

    /**
     * 试验性api 获取好友列表
     *
     * @return 好友列表
     */
    List<_GetFriendListRequset.ResponseData> _getFriendList();

    /**
     * 试验性api 获取扁平化好友列表
     *
     * @return 扁平化的好友列表
     */
    FlatResponseData _getFlatFriendList();

    /**
     * 试验性api 获取群信息
     *
     * @param groupId 群号
     * @return 群详细信息
     */
    _GetGroupInfoRequest.ResponseData _getGroupInfo(long groupId);

    /**
     * 试验性api 获取比较详细的qq号信息
     *
     * @param userId
     * @return
     */
    _GetVipInfoRequest.ResponseData _getVipInfo(long userId);

    /**
     * 试验性api 获取群公告列表
     *
     * @param groupId
     * @return 群公告列表
     */
    List<_GetGroupNoticeRequest.ResponseData> _getGroupNotice(long groupId);

    /**
     * 试验性api 发送群公告 默认异步
     *
     * @param groupId 群
     * @param title   公告标题
     * @param content 公告内容
     * @return 发送群公告是否成功，默认null
     */
    _SendGroupNoticeRequest.Response _sendGroupNotice(long groupId, String title, String content);
}
