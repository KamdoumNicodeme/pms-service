package com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding;

import com.lombardinternational.casemanagement.service.decision.domain.model.rules.Field;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.Group;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.ScreenDescription;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.SelectInputField;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.Tab;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.TaskDefinition;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.TextAreaField;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.TextInputField;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.AmlSignOffOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.ComplexOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.DailyOperationsTeamOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.EbacEscalationOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.FreeSubTaskOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.FreeTaskOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.InadmissibleAssetOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.NtaNotificationOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.PhysicalBacEscalationOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.common.StrategyMonitoringOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.compliance.DefaultOptionalComplianceOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.compliance.EboResidenceTaxComplianceTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.compliance.ItalianBranchPepComplianceTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.compliance.MarketAbuseComplianceTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.compliance.PcsReviewEscalationComplianceTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.compliance.ResidenceVsFiscalCountryComplianceTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.compliance.TccNotSignedComplianceTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.ic.DefaultOptionalICOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.ic.PremiumWithAssetsICOnboardingTaskBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.service.tasklist.onboarding.task.unquoted.DefaultOptionalUnquotedOnboardingTaskBuilderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.BUSINESS_ORIGIN;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.EBO_RESIDENCE_COUNTRY_RISK;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.INSIDER;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.PCS_REVIEW;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.PEP;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.PH_FISCAL_COUNTRY;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.PH_RESIDENCE_COUNTRY_RISK;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.PREMIUM_WITH_ASSETS;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.RISK_REASON;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.TCC_SIGNED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OnboardingTaskListBuilderServiceTest {

    private static final int ONE_DAY = 60 * 60 * 24;
    private static final int TWO_DAYS = ONE_DAY * 2;
    private static final int THIRTY_DAYS = ONE_DAY * 30;

    private OnboardingTaskListBuilderService service;

    @BeforeEach
    void setUp() {

        service = new OnboardingTaskListBuilderService(new PcsReviewEscalationComplianceTaskBuilderService(),
                new MarketAbuseComplianceTaskBuilderService(), new ItalianBranchPepComplianceTaskBuilderService(),
                new EboResidenceTaxComplianceTaskBuilderService(), new ResidenceVsFiscalCountryComplianceTaskBuilderService(),
                new TccNotSignedComplianceTaskBuilderService(), new DefaultOptionalComplianceOnboardingTaskBuilderService(),
                new PremiumWithAssetsICOnboardingTaskBuilderService(), new DefaultOptionalICOnboardingTaskBuilderService(),
                new DefaultOptionalUnquotedOnboardingTaskBuilderService(), new StrategyMonitoringOnboardingTaskBuilderService(),
                new DailyOperationsTeamOnboardingTaskBuilderService(), new ComplexOnboardingTaskBuilderService(),
                new EbacEscalationOnboardingTaskBuilderService(), new PhysicalBacEscalationOnboardingTaskBuilderService(),
                new InadmissibleAssetOnboardingTaskBuilderService(), new NtaNotificationOnboardingTaskBuilderService(),
                new FreeTaskOnboardingTaskBuilderService(), new FreeSubTaskOnboardingTaskBuilderService(),
                new AmlSignOffOnboardingTaskBuilderService());
    }

    @Test
    void buildsDefaultOnboardingTaskList() {

        var tasks = service.buildTaskList(null, null, screen());

        assertIterableEquals(List.of("Compliance check", "IC-Asset", "Unquoted", "Strategy Monitoring", "Daily Operations Team", "Complex",
                "eBAC escalation", "Physical BAC escalation", "Inadmissible Asset", "NTA Notification", "Free Task", "Free Sub-Task",
                "AML sign-off"), tasks.stream().map(TaskDefinition::getMainTaskName).toList());

        assertFalse(task(tasks, "Compliance check").isMandatory());
        assertEquals("COMPLIANCE_NEW_BUSINESS", task(tasks, "Compliance check").getMainTaskDynamicScreenId());
        assertFalse(task(tasks, "IC-Asset").isMandatory());
        assertEquals("IC_NEW_BUSINESS", task(tasks, "IC-Asset").getMainTaskDynamicScreenId());
        assertEquals(THIRTY_DAYS, task(tasks, "Unquoted").getMainTaskExpectedDurationSeconds());
        assertTrue(task(tasks, "Strategy Monitoring").isMandatory());
        assertEquals(List.of("This task is always mandatory"), task(tasks, "Strategy Monitoring").getDecisionReasons());
        assertTrue(task(tasks, "Daily Operations Team").isReleasableFromSubTask());
        assertTrue(task(tasks, "Complex").isReleasableFromSubTask());
        assertEquals(THIRTY_DAYS, task(tasks, "Free Task").getMainTaskExpectedDurationSeconds());
        assertTrue(task(tasks, "Free Sub-Task").isReleasableFromSubTask());
        assertTrue(task(tasks, "AML sign-off").isMandatory());
        assertEquals(60, task(tasks, "AML sign-off").getRulePriority());
    }

    @Test
    void mergesComplianceEscalationReasonsIntoOneMandatoryTask() {

        var tasks = service.buildTaskList(null, null, screen(select(BUSINESS_ORIGIN, "LU"),
                select(PCS_REVIEW, "Senior sign-off"), textArea(RISK_REASON, "Risk reason one\nRisk reason two"),
                select(INSIDER, "YES"), text(PH_RESIDENCE_COUNTRY_RISK, "UK"), text(PH_FISCAL_COUNTRY, "FR, IT"),
                select(TCC_SIGNED, "NO")));

        assertEquals(1, tasks.stream().filter(task -> "Compliance check".equals(task.getMainTaskName())).count());
        var compliance = task(tasks, "Compliance check");
        assertTrue(compliance.isMandatory());
        assertEquals("COMPLIANCE_NEW_BUSINESS", compliance.getMainTaskDynamicScreenId());
        assertTrue(compliance.getDecisionReasons().contains("Risk reason one"));
        assertTrue(compliance.getDecisionReasons().contains("Risk reason two"));
        assertTrue(compliance.getDecisionReasons().contains("Market abuse"));
        assertTrue(compliance.getDecisionReasons().contains(
                "Contact client to obtain explanation (Residence vs fiscal country) or correct information AND If not sensible explanation obtained - Escalation to Compliance"));
        assertTrue(compliance.getDecisionReasons()
                .contains("Check with client why no TCC signed AND Escalation to Tax Compliance - if refused again (No TCC)"));
    }

    @Test
    void doesNotPromoteComplianceForPcsReviewEscalationWithoutRiskReason() {

        var tasks = service.buildTaskList(null, null, screen(select(BUSINESS_ORIGIN, "LU"),
                select(PCS_REVIEW, "Senior sign-off")));

        var compliance = task(tasks, "Compliance check");
        assertFalse(compliance.isMandatory());
        assertTrue(compliance.getDecisionReasons().isEmpty());
    }

    @Test
    void buildsItalianPepAndEboTaxCountryComplianceReasons() {

        var tasks = service.buildTaskList(null, null, screen(select(BUSINESS_ORIGIN, "IT"), select(PEP, "YES"),
                text(EBO_RESIDENCE_COUNTRY_RISK, "HIGH - AS TT")));

        var compliance = task(tasks, "Compliance check");
        assertTrue(compliance.isMandatory());
        assertTrue(compliance.getDecisionReasons().contains("Italian branch PEP"));
        assertTrue(compliance.getDecisionReasons()
                .contains("TCC signed by fiscal adviser OR Tax return AND Escalation to Tax Compliance - if No TCC"));
    }

    @Test
    void buildsMandatoryIcTaskWhenPremiumWithAssets() {

        var tasks = service.buildTaskList(null, null, screen(select(PREMIUM_WITH_ASSETS, "YES")));

        var icAsset = task(tasks, "IC-Asset");
        assertTrue(icAsset.isMandatory());
        assertEquals("IC_NEW_BUSINESS", icAsset.getMainTaskDynamicScreenId());
        assertEquals(TWO_DAYS, icAsset.getMainTaskExpectedDurationSeconds());
        assertEquals(ONE_DAY, icAsset.getSignoffTaskExpectedDurationSeconds());
        assertEquals(List.of("Escalation to Investment Compliance - Premium with assets"), icAsset.getDecisionReasons());
    }

    private TaskDefinition task(final List<TaskDefinition> tasks, final String taskName) {

        return tasks.stream().filter(task -> taskName.equals(task.getMainTaskName())).findFirst().orElseThrow();
    }

    private ScreenDescription screen(final Field... fields) {

        return ScreenDescription.builder().tabs(List.of(Tab.builder()
                .groups(List.of(Group.builder().groupId("CHECKLIST").fields(List.of(fields)).build())).build())).build();
    }

    private SelectInputField select(final String fieldId, final String value) {

        return SelectInputField.builder().fieldId(fieldId).selectedValue(value).build();
    }

    private TextInputField text(final String fieldId, final String value) {

        return TextInputField.builder().fieldId(fieldId).selectedValue(value).build();
    }

    private TextAreaField textArea(final String fieldId, final String value) {

        return TextAreaField.builder().fieldId(fieldId).selectedValue(value).build();
    }
}
