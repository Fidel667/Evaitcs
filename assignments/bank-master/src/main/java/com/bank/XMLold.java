<!-- fintech-beans.xml -->
<beans>

    <!-- 1. Create a simple Bean with constructor value -->
    <bean id="feeStrategy"
class="com.bank.PercentageFee">
        <constructor-arg value="0.01"/>
        </bean>

        <!-- 2. Setter Injection -->
    <bean id="auditLogger"
class="com.bank.AuditLogger">
        <property name="logLevel" value="INFO"/>
    </bean>

    <!-- 3. Wire Beans together using ref -->
    <bean id="paymentService"
class="com.bank.PaymentService">
        <constructor-arg ref="feeStrategy"/>
        <constructor-arg ref="auditLogger"/>
    </bean>

</beans>