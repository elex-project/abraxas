/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Elex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Nullable;

/**
 * @see "https://developer.mozilla.org/ko/docs/Web/HTTP/Status"
 * @see "https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C"
 */
public enum HttpStatus {
	/**
	 * 이 임시적인 응답은 지금까지의 상태가 괜찮으며 클라이언트가 계속해서 요청을 하거나 이미 요청을 완료한 경우에는 무시해도 되는 것을 알려줍니다.
	 */
	CONTINUE(100, "Continue"),
	/**
	 * 이 코드는 클라이언트가 보낸 Upgrade 요청 헤더에 대한 응답에 들어가며 서버에서 프로토콜을 변경할 것임을 알려줍니다.
	 */
	SWITCHING_PROTOCOL(101, "Switching Protocol"),
	/**
	 * 이 코드는 서버가 요청을 수신하였으며 이를 처리하고 있지만, 아직 제대로 된 응답을 알려줄 수 없음을 알려줍니다.
	 * (WebDAV)
	 */
	PROCESSING(102, "Processing"),

	/**
	 * 요청이 성공적으로 되었습니다. 성공의 의미는 HTTP 메소드에 따라 달라집니다:
	 * GET: 리소스를 불러와서 메시지 바디에 전송되었습니다.
	 * HEAD: 개체 해더가 메시지 바디에 있습니다.
	 * PUT 또는 POST: 수행 결과에 대한 리소스가 메시지 바디에 전송되었습니다.
	 * TRACE: 메시지 바디는 서버에서 수신한 요청 메시지를 포함하고 있습니다.
	 */
	OK(200, "OK"),
	/**
	 * 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성되었습니다. 이 응답은 일반적으로 POST 요청 또는 일부 PUT 요청 이후에 따라옵니다.
	 */
	CREATED(201, "Created"),
	/**
	 * 요청을 수신하였지만 그에 응하여 행동할 수 없습니다. 이 응답은 요청 처리에 대한 결과를 이후에 HTTP로 비동기 응답을 보내는 것에 대해서 명확하게 명시하지 않습니다. 이것은 다른 프로세스에서 처리 또는 서버가 요청을 다루고 있거나 배치 프로세스를 하고 있는 경우를 위해 만들어졌습니다.
	 */
	ACCEPTED(202, "Accepted"),
	/**
	 * 이 응답 코드는 돌려받은 메타 정보 세트가 오리진 서버의 것과 일치하지 않지만 로컬이나 서드 파티 복사본에서 모아졌음을 의미합니다. 이러한 조건에서는 이 응답이 아니라 200 OK 응답을 반드시 우선됩니다.
	 */
	NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
	/**
	 * 요청에 대해서 보내줄 수 있는 콘텐츠가 없지만, 헤더는 의미있을 수 있습니다. 사용자-에이전트는 리소스가 캐시된 헤더를 새로운 것으로 업데이트 할 수 있습니다.
	 */
	NO_CONTENT(204, "No Content"),
	/**
	 * 이 응답 코드는 요청을 완수한 이후에 사용자 에이전트에게 이 요청을 보낸 문서 뷰를 리셋하라고 알려줍니다.
	 */
	RESET_CONTENT(205, "Reset Content"),
	/**
	 * 이 응답 코드는 클라이언트에서 복수의 스트림을 분할 다운로드를 하고자 범위 헤더를 전송했기 때문에 사용됩니다.
	 */
	PARTIAL_CONTENT(206, "Partial Content"),
	/**
	 * 멀티-상태 응답은 여러 리소스가 여러 상태 코드인 상황이 적절한 경우에 해당되는 정보를 전달합니다.
	 * (WebDAV)
	 */
	MULTI_STATUS(207, "Multi-Status"),
	/**
	 * DAV에서 사용됩니다: propstat(property와 status의 합성어) 응답 속성으로 동일 컬렉션으로 바인드된 복수의 내부 멤버를 반복적으로 열거하는 것을 피하기 위해 사용됩니다.
	 * (WebDAV)
	 */
	ALREADY_REPORTED(208, "Already Reported"),
	/**
	 * 서버가 GET 요청에 대한 리소스의 의무를 다 했고, 그리고 응답이 하나 또는 그 이상의 인스턴스 조작이 현재 인스턴스에 적용이 되었음을 알려줍니다.
	 * (HTTP Delta encoding)
	 */
	IM_USED(226, "IM Used"),

	/**
	 * 요청에 대해서 하나 이상의 응답이 가능합니다. 사용자 에이전트 또는 사용자는 그중에 하나를 반드시 선택해야 합니다. 응답 중 하나를 선택하는 방법에 대한 표준화 된 방법은 존재하지 않습니다.
	 */
	MULTIPLE_CHOICE(300, "Multiple Choice"),
	/**
	 * 이 응답 코드는 요청한 리소스의 URI가 변경되었음을 의미합니다. 새로운 URI가 응답에서 아마도 주어질 수 있습니다.
	 */
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	/**
	 * 이 응답 코드는 요청한 리소스의 URI가 일시적으로 변경되었음을 의미합니다. 새롭게 변경된 URI는 나중에 만들어질 수 있습니다. 그러므로, 클라이언트는 향후의 요청도 반드시 동일한 URI로 해야합니다.
	 */
	FOUND(302, "Found"),
	/**
	 * 클라이언트가 요청한 리소스를 다른 URI에서 GET 요청을 통해 얻어야 할 때, 서버가 클라이언트로 직접 보내는 응답입니다.
	 */
	SEE_OTHER(303, "See Other"),
	/**
	 * 이것은 캐시를 목적으로 사용됩니다. 이것은 클라이언트에게 응답이 수정되지 않았음을 알려주며, 그러므로 클라이언트는 계속해서 응답의 캐시된 버전을 사용할 수 있습니다.
	 */
	NOT_MODIFIED(304, "Not Modified"),
	/**
	 * 이전 버전의 HTTP 기술 사양에서 정의되었으며, 요청한 응답은 반드시 프록시를 통해서 접속해야 하는 것을 알려줍니다. 이것은 프록시의 in-band 설정에 대한 보안상의 걱정으로 인하여 사라져가고 있습니다.
	 */
	USE_PROXY(305, "Use Proxy"),
	/**
	 * 클라리언트가 요청한 리소스가 다른 URI에 있으며, 이전 요청과 동일한 메소드를 사용하여 요청해야할 때, 서버가 클라이언트에 이 응답을 직접 보냅니다. 이것은 302 Found HTTP 응답 코드와 동일한 의미를 가지고 있으며, 사용자 에이전트가 반드시 사용된 HTTP 메소드를 변경하지 말아야 하는 점만 다릅니다: 만약 첫 요청에 POST가 사용되었다면, 두번째 요청도 반드시 POST를 사용해야 합니다.
	 */
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	/**
	 * 이것은 리소스가 이제 HTTP 응답 헤더의 Location: 에 명시된 영구히 다른 URI에 위치하고 있음을 의미합니다. 이것은 301 Moved Permanently HTTP 응답 코드와 동일한  의미를 가지고 있으며, 사용자 에이전트가 반드시 HTTP 메소드를 변경하지 말아야 하는 점만 다릅니다: 만약 첫 요청에 POST가 사용되었다면, 두번째 요청도 반드시 POST를 사용해야 합니다.
	 */
	PERMANENT_REDIRECT(308, "Permanent Redirect"),

	/**
	 * 이 응답은 잘못된 문법으로 인하여 서버가 요청을 이해할 수 없음을 의미합니다.
	 */
	BAD_REQUEST(400, "Bad Request"),
	/**
	 * 비록 HTTP 표준에서는 "미승인(unauthorized)"를 명확히 하고 있지만, 의미상 이 응답은 "비인증(unauthenticated)"을 의미합니다. 클라이언트는 요청한 응답을 받기 위해서는 반드시 스스로를 인증해야 합니다.
	 */
	UNAUTHORIZED(401, "Unauthorized"),
	/**
	 * 이 응답 코드는 나중에 사용될 것을 대비해 예약되었습니다. 첫 목표로는 디지털 결제 시스템에 사용하기 위하여 만들어졌지만 지금 사용되고 있지는 않습니다.
	 */
	PAYMENT_REQUIRED(402, "Payment Required"),
	/**
	 * 클라이언트는 콘텐츠에 접근할 권리를 가지고 있지 않습니다.
	 * 예를들어 그들은 미승인이어서 서버는 거절을 위한 적절한 응답을 보냅니다.
	 * 401과 다른 점은 서버가 클라이언트가 누구인지 알고 있습니다.
	 */
	FORBIDDEN(403, "Forbidden"),
	/**
	 * 서버는 요청받은 리소스를 찾을 수 없습니다. 브라우저에서는 알려지지 않은 URL을 의미합니다.
	 * 이것은 API에서 종점은 적절하지만 리소스 자체는 존재하지 않음을 의미할 수도 있습니다. 서버들은 인증받지 않은 클라이언트로부터 리소스를 숨기기 위하여 이 응답을 403 대신에 전송할 수도 있습니다. 이 응답 코드는 웹에서 반복적으로 발생하기 때문에 가장 유명할지도 모릅니다.
	 */
	NOT_FOUND(404, "Not Found"),
	/**
	 * 요청한 메소드는 서버에서 알고 있지만, 제거되었고 사용할 수 없습니다. 예를 들어, 어떤 API에서 리소스를 삭제하는 것을 금지할 수 있습니다. 필수적인 메소드인 GET과 HEAD는 제거될 수 없으며 이 에러 코드를 리턴할 수 없습니다.
	 */
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	/**
	 * 이 응답은 서버가 서버 주도 콘텐츠 협상 을 수행한 이후, 사용자 에이전트에서 정해준 규격에 따른 어떠한 콘텐츠도 찾지 않았을 때, 웹서버가 보냅니다.
	 */
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	/**
	 * 이것은 401과 비슷하지만 프록시에 의해 완료된 인증이 필요합니다.
	 */
	PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
	/**
	 * 이 응답은 요청을 한지 시간이 오래된 연결에 일부 서버가 전송하며, 어떨 때에는 이전에 클라이언트로부터 어떠한 요청이 없었다고 하더라도 보내지기도 합니다. 이것은 서버가 사용되지 않는 연결을 끊고 싶어한다는 것을 의미합니다. 이 응답은 특정 몇몇 브라우저에서 빈번하게 보이는데, Chrome, Firefox 27+, 또는 IE9와 같은 웹서핑 속도를 올리기 위해 HTTP 사전 연결 메카니즘을 사용하는 브라우저들이 해당됩니다. 또한 일부 서버는 이 메시지를 보내지 않고 연결을 끊어버리기도 합니다.
	 */
	REQUEST_TIMEOUT(408, "Request Timeout"),
	/**
	 * 이 응답은 요청이 현재 서버의 상태와 충돌될 때 보냅니다.
	 */
	CONFLICT(409, "Conflict"),
	/**
	 * 이 응답은 요청한 콘텐츠가 서버에서 영구적으로 삭제되었으며, 전달해 줄 수 있는 주소 역시 존재하지 않을 때 보냅니다. 클라이언트가 그들의 캐쉬와 리소스에 대한 링크를 지우기를 기대합니다. HTTP 기술 사양은 이 상태 코드가 "일시적인, 홍보용 서비스"에 사용되기를 기대합니다. API는 알려진 리소스가 이 상태 코드와 함께 삭제되었다고 강요해서는 안된다.
	 */
	GONE(410, "Gone"),
	/**
	 * 서버에서 필요로 하는 Content-Length 헤더 필드가 정의되지 않은 요청이 들어왔기 때문에 서버가 요청을 거절합니다.
	 */
	LENGTH_REQUIRED(411, "Length Required"),
	/**
	 * 클라이언트의 헤더에 있는 전제조건은 서버의 전제조건에 적절하지 않습니다.
	 */
	PRECONDITION_FAILED(412, "Precondition Failed"),
	/**
	 * 요청 엔티티는 서버에서 정의한 한계보다 큽니다; 서버는 연결을 끊거나 혹은 Retry-After 헤더 필드로 돌려보낼 것이다.
	 */
	PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
	/**
	 * 클라이언트가 요청한 URI는 서버에서 처리하지 않기로 한 길이보다 깁니다.
	 */
	URI_TOO_LONG(414, "URI Too Long"),
	/**
	 * 요청한 미디어 포맷은 서버에서 지원하지 않습니다, 서버는 해당 요청을 거절할 것입니다.
	 */
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	/**
	 * Range 헤더 필드에 요청한 지정 범위를 만족시킬 수 없습니다; 범위가 타겟 URI 데이터의 크기를 벗어났을 가능성이 있습니다.
	 */
	REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
	/**
	 * 이 응답 코드는 Expect 요청 헤더 필드로 요청한 예상이 서버에서는 적당하지 않음을 알려줍니다.
	 */
	EXPECTATION_FAILED(417, "Expectation Failed"),
	IM_A_TEAPOT(418, "I'm a teapot"),
	/**
	 * 서버로 유도된 요청은 응답을 생성할 수 없습니다. 이것은 서버에서 요청 URI와 연결된 스킴과 권한을 구성하여 응답을 생성할 수 없을 때 보내집니다.
	 */
	MISDIRECTED_REQUEST(421, "Misdirected Request"),
	/**
	 * 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
	 * (WebDAV)
	 */
	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

	/**
	 * 리소스는 접근하는 것이 잠겨있습니다.
	 * (WebDAV)
	 */
	LOCKED(423, "Locked"),
	/**
	 * 이전 요청이 실패하였기 때문에 지금의 요청도 실패하였습니다.
	 * (WebDAV)
	 */
	FAILED_DEPENDENCY(424, "Failed Dependency"),
	/**
	 * 서버는 지금의 프로토콜을 사용하여 요청을 처리하는 것을 거절하였지만, 클라이언트가 다른 프로토콜로 업그레이드를 하면 처리를 할지도 모릅니다. 서버는 Upgrade 헤더와 필요로 하는 프로토콜을 알려주기 위해 426 응답에 보냅니다.
	 */
	UPGRADE_REQUIRED(426, "Upgrade Required"),
	/**
	 * 오리진 서버는 요청이 조건적이어야 합니다. 클라이언트가 리소스를 GET해서, 수정하고, 그리고 PUT으로 서버에 돌려놓는 동안 서드파티가 서버의 상태를 수정하여 발생하는 충돌인 '업데이트 상실'을 예방하기 위한 목적입니다.
	 */
	PRECONDITION_REQUIRED(428, "Precondition Required"),
	/**
	 * 사용자가 지정된 시간에 너무 많은 요청을 보냈습니다("rate limiting").
	 */
	TOO_MANY_REDIRECTS(429, "Too Many Requests"),
	/**
	 * 요청한 헤더 필드가 너무 크기 때문에 서버는 요청을 처리하지 않을 것입니다. 요청은 크기를 줄인 다음에 다시 전송해야 합니다.
	 */
	REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
	/**
	 * 사용자가 요청한 것은 정부에 의해 검열된 웹 페이지와 같은 불법적인 리소스입니다.
	 */
	UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

	/**
	 * 서버가 처리 방법을 모르는 상황과 마주쳤다. 서버는 아직 처리 방법을 알 수 없다.
	 */
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	/**
	 * 요청 방법은 서버에서 지원되지 않으므로 처리할 수 없다. 서버가 지원해야 하는 유일한 방법은 GET와 HEAD이다. 이 코드는 반환하면 안됩니다.
	 */
	NOT_IMPLEMENTED(501, "Not Implemented"),
	/**
	 * 이 오류 응답은 서버가 요청을 처리하는 데 필요한 응답을 얻기 위해 게이트웨이로 작업하는 동안 잘못된 응답을 수신했음을 의미한다.
	 */
	BAD_GATEWAY(502, "Bad Gateway"),
	/**
	 * 서버가 요청을 처리할 준비가 되지 않았다. 일반적인 원인은 유지보수를 위해 작동이 중단되거나 과부하가 걸린 서버다.
	 * 이 응답과 함께 문제를 설명하는 사용자 친화적인 페이지가 전송되어야 한다는 점에 유의하십시오.
	 * 이 응답은 임시 조건에 사용되어야 하며, Retry-After: HTTP 헤더는 가능하면 서비스를 복구하기 전 예상 시간을 포함해야 한다.
	 * 웹마스터는 또한 이러한 일시적인 조건 응답을 캐시하지 않아야 하므로 이 응답과 함께 전송되는 캐싱 관련 헤더에 대해서도 주의해야 한다.
	 */
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	/**
	 * 이 오류 응답은 서버가 게이트웨이 역할을 하고 있으며 적시에 응답을 받을 수 없을 때 주어진다.
	 */
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	/**
	 * 요청에 사용된 HTTP 버전은 서버에서 지원되지 않음.
	 */
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
	/**
	 * 서버에 내부 구성 오류가 있다. 즉, 요청을 위한 투명한 컨텐츠 협상이 순환 참조로 이어진다.
	 */
	VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
	/**
	 * 서버에 내부 구성 오류가 있다. 즉, 선택한 가변 리소스는 투명한 콘텐츠 협상에 참여하도록 구성되므로 협상 과정에서 적절한 끝점이 아니다.
	 */
	INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
	/**
	 * 서버가 요청을 처리하는 동안 무한 루프를 감지했다.
	 * (WebDAV)
	 */
	LOOP_DETECTED(508, "Loop Detected"),
	/**
	 * 서버가 요청을 이행하려면 요청에 대한 추가 확장이 필요하다.
	 */
	NOT_EXTENDED(510, "Not Extended"),
	/**
	 * 511 상태 코드는 클라이언트가 네트워크 액세스를 얻기 위해 인증할 필요가 있음을 나타낸다.
	 */
	NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");


	private final int status;
	private final String message;

	HttpStatus(int code, String message) {
		this.status = code;
		this.message = message;
	}

	/**
	 * @param status
	 * @return 정의되지 않은 상태코드를 입력하면 null을 반환.
	 */
	@Nullable
	public static HttpStatus of(int status) {
		for (HttpStatus item : values()) {
			if (item.status == status) {
				return item;
			}
		}
		return null;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return Stringz.format("{} - {}", status, message);
	}
}