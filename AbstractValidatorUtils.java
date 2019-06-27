public abstract class AbstractValidatorUtilsTest implements Validator {

	/**
	 * 正規表現パターン：メールアドレスフォーマット
	 */
	private static final Pattern REGEXP_EMAIL_FORMAT = Pattern.compile(
			"^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]?[a-zA-Z0-9.!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$");
	/**
	 * 正規表現パターン：半角英数＆ハイフン（-）
	 */
	private static final Pattern REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_CHARACTERS = Pattern.compile("^[0-9a-zA-Z\\-]+$");
	/**
	 * 正規表現パターン：半角英数＆ハイフン（-）＆改行コード
	 */
	private static final Pattern REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_LINEFEED_CHARACTERS = Pattern
			.compile("^[0-9a-zA-Z\\-\r\n]+$");
	/**
	 * 正規表現パターン：改行コード
	 */
	private static final Pattern REGEXP_LINEFEED_CHARACTERS = Pattern.compile("^[\r\n]+$");
	/**
	 * 正規表現パターン：半角英数＆ハイフン（-）＆カンマ（,）
	 */
	private static final Pattern REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_COMMMA_CHARACTERS = Pattern
			.compile("^[0-9a-zA-Z\\-,]+$");
	/**
	 * 正規表現パターン：半角英数記号
	 */
	private static final Pattern REGEXP_HALF_ALPHANUMERIC_SYMBOL_CHARACTERS = Pattern
			.compile("^[a-zA-Z0-9!-/:-@\\[-\\`\\{-\\~]+$");
	/**
	 * 正規表現パターン：半角英数
	 */
	private static final Pattern REGEXP_HALF_ALPHANUMERIC_CHARACTERS = Pattern.compile("^[a-zA-Z0-9]+$");
	/**
	 * 正規表現パターン：全角文字
	 */
	private static final Pattern REGEXP_FULL_CHARACTERS = Pattern.compile("^[^ -~｡-ﾟ]+$");
	/**
	 * 正規表現パターン：全角カナ文字
	 */
	private static final Pattern REGEXP_FULL_WIDTH_KATAKANA = Pattern.compile("^[ァ-ー]+$");
	/**
	 * 正規表現パターン：半角数字
	 */
	private static final Pattern REGEXP_HALF_NUMERIC_CHARACTERS = Pattern.compile("^[\\d]+$");
	/**
	 * 正規表現パターン：半角文字
	 */
	private static final Pattern REGEXP_HALF_WIDTH_CHARACTERS = Pattern.compile("^[ -~｡-ﾟ]+$");
	/**
	 * 正規表現パターン：楽天注文番号（******-yyyymmdd-9999999999）
	 */
	private static final Pattern REGEXP_RAKUTEN_ORDER_NUMBER = Pattern.compile("\\d{6}-\\d{8}-\\d{7,10}");
	/**
	 * 正規表現パターン：禁則文字チェック（メールアドレス）【0-9】 【a-z】 【A-Z】 【-】 【@】【_】【.】【+】
	 */
	private static final Pattern REGEXP_EMAIL_CARACTERS = Pattern.compile("^[0-9a-zA-Z\\-@_\\.\\+]+$");
	/**
	 * 正規表現パターン：【－】（全角マイナス）、【〜】Unicodeの波線
	 */
	private static final Pattern REGEXP_FULL_WIDTH_MINUS = Pattern.compile("^[－〜]+$");
	/**
	 * 正規表現パターン：IBM機種依存文字 記号部分
	 */
	private static final Pattern REGEXP_IBM_SYMBOL = Pattern.compile("^[ⅰⅱⅲⅳⅴⅵⅶⅷⅸⅹⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ￢￤＇＂㈱№℡∵]+$");

	/**
	 * Default Constructor.
	 */
	public AbstractValidatorUtils() {
	}

	/**
	 * @return the messageUtils
	 */
	public MessageUtils getMessageUtils() {
		return messageUtils;
	}

	/**
	 * @param messageUtils the messageUtils to set
	 */
	public void setMessageUtils(final MessageUtils messageUtils) {
		this.messageUtils = messageUtils;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the enumerationService
	 */
	public EnumerationService getEnumerationService() {
		return enumerationService;
	}

	/**
	 * @param enumerationService the enumerationService to set
	 */
	public void setEnumerationService(final EnumerationService enumerationService) {
		this.enumerationService = enumerationService;
	}

	/**
	 * 必須チェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkRequired(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isBlank(source)) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 必須チェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkRequired(final Errors errors, final Object source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && source == null) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字列長（最大）チェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param maxLength         最大文字列長
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkMaxLength(final Errors errors, final String source, final int maxLength, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& StringUtils.length(source) > maxLength) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey), String.valueOf(maxLength) },
					getMessageUtils().getMessage(attributeLabelKey, String.valueOf(maxLength)));
		}
	}

	/**
	 * 文字列長（最小、最大）チェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param minLength         最小文字列長
	 * @param maxLength         最大文字列長
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkMinMaxLength(final Errors errors, final String source, final int minLength, final int maxLength,
			final String attributeKey, final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& (StringUtils.length(source) < minLength || StringUtils.length(source) > maxLength)) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey), String.valueOf(maxLength) },
					getMessageUtils().getMessage(attributeLabelKey, String.valueOf(minLength),
							String.valueOf(maxLength)));
		}
	}

	/**
	 * メールアドレス フォーマットチェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkEmailFormat(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_EMAIL_FORMAT.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * メールアドレス存在チェック
	 *
	 * @param source メールアドレス
	 * @return true/false
	 */
	public boolean isExistEmailAddress(final String source) {
		boolean result = false;
		try {
			final UserModel user = getUserService().getUserForUID(source.toLowerCase());
			if (user != null) {
				result = true;
			} else {
				result = false;
			}
		} catch (final UnknownIdentifierException e) {
			result = false;
		}
		return result;
	}

	/**
	 * パスワードチェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkPassword(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字列長（最小、最大）チェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param minLength         最小文字列長
	 * @param maxLength         最大文字列長
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkRangeLength(final Errors errors, final String source, final int minLength, final int maxLength,
			final String attributeKey, final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& (StringUtils.length(source) < minLength || StringUtils.length(source) > maxLength)) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey), String.valueOf(minLength),
							String.valueOf(maxLength) },
					getMessageUtils().getMessage(attributeLabelKey, String.valueOf(minLength),
							String.valueOf(maxLength)));
		}
	}

	/**
	 * 文字種チェック：全角文字
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkFullWidthCharactersForName(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_FULL_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字種チェック：全角カナ
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkFullWidthCharactersForNameKana(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_FULL_WIDTH_KATAKANA.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));

		}
	}

	/**
	 * 文字種チェック：半角数字
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkHalfWidthNumericCharacters(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_HALF_NUMERIC_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字種チェック：全角文字
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	protected void checkFullWidthCharactersForCity(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_FULL_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字種チェック：全角文字
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	protected void checkFullWidthCharactersForTownArea(final Errors errors, final String source,
			final String attributeKey, final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_FULL_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字種チェック：半角文字
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	protected void checkHalfWidthCharacters(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_HALF_WIDTH_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 丁目以降_フィールド１ 先頭文字チェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	protected void checkFirstCharacter(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& source.charAt(0) == '0') {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 必須チェック
	 *
	 * @param errors            Errors
	 * @param source1           対象要素1
	 * @param source2           対象要素2
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkRequired(final Errors errors, final String source1, final String source2,
			final String attributeKey, final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey))
				&& (StringUtils.isBlank(source1) || StringUtils.isBlank(source2))) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 有効日付チェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkValidDate(final Errors errors, final String date, final String attributeKey,
			final String attributeLabelKey) {
		if (!isValidDate(date)) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 日付フォーマットチェック
	 *
	 * @param inDate 日付
	 * @return true/false
	 */
	public boolean isValidDate(final String inDate) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			final Date parsedDate = dateFormat.parse(inDate.trim());
			return new Date().after(parsedDate);
		} catch (final ParseException e) {
			return false;
		}
	}

	/**
	 * 文字種チェック：半角英数＆ハイフン（-）
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */

	protected void checkHalfWidthNumericHyphenCharacters(final Errors errors, final String source,
			final String attributeKey, final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字種チェック：半角英数＆ハイフン（-）＆カンマ（,）
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	protected void checkHalfWidthNumericHyphenCommaCharacters(final Errors errors, final String source,
			final String attributeKey, final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_COMMMA_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字種チェック：半角英数
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkHalfWidthAlpaNumericCharacters(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_HALF_ALPHANUMERIC_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 文字種チェック：半角英数記号
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkHalfWidthAlpaNumericSymbolCharacters(final Errors errors, final String source,
			final String attributeKey, final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_HALF_ALPHANUMERIC_SYMBOL_CHARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 楽天注文番号フォーマットチェック
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	protected void checkRakutenOrderNumber(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {
		if (CollectionUtils.isEmpty(errors.getFieldErrors(attributeKey)) && StringUtils.isNotBlank(source)
				&& !REGEXP_RAKUTEN_ORDER_NUMBER.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey,
					new Object[] { getMessageUtils().getMessage(attributeLabelKey) },
					getMessageUtils().getMessage(attributeLabelKey));
		}
	}

	/**
	 * 禁則文字チェック：全角、半角入力<br>
	 * ・SJIS第1、第2水準以外の文字<br>
	 * ・【0-9】 【a-z】 【A-Z】 【-】 以外の半角文字<br>
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkKinsokuFullHalfWidth(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {

		if (CollectionUtils.isNotEmpty(errors.getFieldErrors(attributeKey)) || StringUtils.isBlank(source)) {
			return;
		}

		for (int i = 0; i < source.length(); i++) {
			final char ch = source.charAt(i);
			// ホワイトリスト（入力許可文字）
			// ・SJIS第1、第2水準
			// ・【0-9】 【a-z】 【A-Z】 【-】
			if (!checkKinsokuValidSjisCharactor(ch)
					&& !REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_CHARACTERS.matcher(String.valueOf(ch)).matches()) {
				errors.rejectValue(attributeKey, attributeLabelKey, getMessageUtils().getMessage(attributeLabelKey));
				break;
			}
		}
	}

	/**
	 * 禁則文字チェック：全角、半角入力（改行含む）<br>
	 * ・SJIS第1、第2水準以外の文字<br>
	 * ・【0-9】 【a-z】 【A-Z】 【-】 以外の半角文字<br>
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkKinsokuFullHalfWidthWithLineFeed(final Errors errors, final String source,
			final String attributeKey, final String attributeLabelKey) {

		if (CollectionUtils.isNotEmpty(errors.getFieldErrors(attributeKey)) || StringUtils.isBlank(source)) {
			return;
		}

		for (int i = 0; i < source.length(); i++) {
			final char ch = source.charAt(i);
			// ホワイトリスト（入力許可文字）
			// ・SJIS第1、第2水準
			// ・【0-9】 【a-z】 【A-Z】 【-】
			// ・改行コード
			if (!checkKinsokuValidSjisCharactor(ch)
					&& !REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_LINEFEED_CHARACTERS.matcher(String.valueOf(ch)).matches()) {
				errors.rejectValue(attributeKey, attributeLabelKey, getMessageUtils().getMessage(attributeLabelKey));
				break;
			}
		}
	}

	/**
	 * 禁則文字チェック：全角文字入力<br>
	 * ・SJIS第1、第2水準以外の文字<br>
	 * ・半角文字<br>
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkKinsokuFullWidth(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {

		if (CollectionUtils.isNotEmpty(errors.getFieldErrors(attributeKey)) || StringUtils.isBlank(source)) {
			return;
		}

		for (int i = 0; i < source.length(); i++) {
			// ホワイトリスト（入力許可文字）
			// ・SJIS第1/第2水準
			if (!checkKinsokuValidSjisCharactor(source.charAt(i))) {
				errors.rejectValue(attributeKey, attributeLabelKey, getMessageUtils().getMessage(attributeLabelKey));
				break;
			}
			// ・半角文字 ⇒ チェック内容が重複するため省略
		}
	}

	/**
	 * 禁則文字チェック：全角文字入力（改行含む）<br>
	 * ・SJIS第1、第2水準以外の文字<br>
	 * ・半角文字<br>
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkKinsokuFullWidthWithLineFeed(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {

		if (CollectionUtils.isNotEmpty(errors.getFieldErrors(attributeKey)) || StringUtils.isBlank(source)) {
			return;
		}

		for (int i = 0; i < source.length(); i++) {
			final char ch = source.charAt(i);
			// ホワイトリスト（入力許可文字）
			// ・SJIS第1/第2水準
			// ・改行コード
			if (!checkKinsokuValidSjisCharactor(ch)
					&& !REGEXP_LINEFEED_CHARACTERS.matcher(String.valueOf(ch)).matches()) {
				errors.rejectValue(attributeKey, attributeLabelKey, getMessageUtils().getMessage(attributeLabelKey));
				break;
			}
			// ・半角文字 ⇒ チェック内容が重複するため省略
		}
	}

	/**
	 * 禁則文字チェック：メールアドレス入力<br>
	 * ・SJIS第1、第2水準以外の文字 <br>
	 * ・【0-9】 【a-z】 【A-Z】 【-】 【@】【_】【.】【+】以外の半角文字<br>
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkKinsokuMail(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {

		if (CollectionUtils.isNotEmpty(errors.getFieldErrors(attributeKey)) || StringUtils.isBlank(source)) {
			return;
		}
		// ホワイトリスト（入力許可文字）
		// ・【0-9】【a-z】【A-Z】【-】【@】【_】【.】【+】
		if (!REGEXP_EMAIL_CARACTERS.matcher(source).matches()) {
			errors.rejectValue(attributeKey, attributeLabelKey, getMessageUtils().getMessage(attributeLabelKey));
		}
		// 全角文字入力は許容されないため、全角文字チェックは行わない
		// （上記の半角文字チェックに包含される）
	}

	/**
	 * 禁則文字チェック：町域、建物名称入力<br>
	 * ・SJIS第1、第2水準以外の文字<br>
	 * ・【[TAB]】 【'】 【】 【,】 【[】 【]】 【－】<br>
	 * ・【0-9】 【a-z】 【A-Z】 【-】 以外の半角文字<br>
	 *
	 * @param errors            Errors
	 * @param source            対象要素
	 * @param attributeKey      フィールド名
	 * @param attributeLabelKey メッセージID
	 */
	public void checkKinsokuTownAreaAndBuildingName(final Errors errors, final String source, final String attributeKey,
			final String attributeLabelKey) {

		if (CollectionUtils.isNotEmpty(errors.getFieldErrors(attributeKey)) || StringUtils.isBlank(source)) {
			return;
		}

		for (int i = 0; i < source.length(); i++) {
			final char ch = source.charAt(i);
			boolean isValidInput = false;
			if (REGEXP_FULL_WIDTH_MINUS.matcher(String.valueOf(ch)).matches()) {
				// ブラックリスト（入力不許可文字）
				// ・【－】全角マイナス、【〜】Unicodeの波線
				isValidInput = false;
			} else if (checkKinsokuValidSjisCharactor(ch)) {
				// ホワイトリスト（入力許可文字）
				// ・SJIS第1/第2水準/IBM機種依存文字
				isValidInput = true;
			} else if (REGEXP_HALF_ALPHA_NUMERIC_HYPHEN_CHARACTERS.matcher(String.valueOf(ch)).matches()) {
				// ホワイトリスト（入力許可文字）
				// ・【0-9】【a-z】【A-Z】【-】
				isValidInput = true;
			}
			if (!isValidInput) {
				errors.rejectValue(attributeKey, attributeLabelKey, getMessageUtils().getMessage(attributeLabelKey));
				break;
			}
		}
	}

	/**
	 * 禁則文字チェック（SJIS 第1水準/第2水準）<br>
	 * ・SJIS第1、第2水準以外の文字<br>
	 *
	 * @param ch 対象文字
	 * @return true（OK）、false（NG）
	 */
	protected boolean checkKinsokuValidSjisCharactor(final char ch) {

		// UTF-8 ⇒ SJIS変換
		final byte[] sjisBytes = String.valueOf(ch).getBytes(Charset.forName("MS932"));
		final int sjisCode = new BigInteger(1, sjisBytes).intValue();

		boolean isValidSjisCharactor = false;
		if (0x8140 <= sjisCode && sjisCode <= 0x84be) {
			// 全角 記号/英数字/かな「（全角スペース）」～「╂」
			isValidSjisCharactor = true;
		} else if (0x889f <= sjisCode && sjisCode <= 0x9872) {
			// JIS第1水準「亜」～「腕」
			isValidSjisCharactor = true;
		} else if ((0x989f <= sjisCode && sjisCode <= 0x9ffc) || (0xe040 <= sjisCode && sjisCode <= 0xeaa4)) {
			// JIS第2水準「弌」～「滌」「漾」～「熙」
			isValidSjisCharactor = true;
		} else if (isIbmCharacter(ch)) {
			// IBM機種依存文字
			isValidSjisCharactor = true;
		}
		return isValidSjisCharactor;
	}

	/**
	 * IBM機種依存文字チェック
	 *
	 * @param ch 確認対象文字
	 * @return true（IBM機種依存文字に含まれる）、false（IBM機種依存文字に含まれない）
	 */
	protected boolean isIbmCharacter(final char ch) {

		// UTF-8 ⇒ SJIS変換
		final byte[] sjisBytes = String.valueOf(ch).getBytes(Charset.forName("MS932"));
		final int sjisCode = new BigInteger(1, sjisBytes).intValue();

		boolean isIbmCharacter = false;
		if (REGEXP_IBM_SYMBOL.matcher(String.valueOf(ch)).matches() || (0xfa40 <= sjisCode && sjisCode <= 0xfc4b)) {
			// IBM機種依存文字「ⅰ」～「黑」
			// （記号部分はUTF-8のまま正規表現でチェックを行う）
			isIbmCharacter = true;
		}
		return isIbmCharacter;
	}
}
