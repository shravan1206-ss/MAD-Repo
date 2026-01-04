package com.example.studymate.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studymate.db.entity.StudyPlanEntity
import com.example.studymate.db.modules.DatabaseModule
import com.example.studymate.notification.ReminderScheduler
import com.example.studymate.ui.theme.AppBackground
import com.example.studymate.ui.theme.StudyMateTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectPlannerScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DatabaseModule.getDb(context)
    val dao = db.studyPlanDao()
    val scope = rememberCoroutineScope()

    val plans by dao.getAllPlans().collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Planner 🗓️") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.shadow(8.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->

        AppBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(plans, key = { it.id }) { plan ->
                    SubjectPlanCard(
                        subject = plan.subject,
                        date = formatDate(plan.dateMillis),
                        time = formatTime(plan.timeMillis),
                        onDelete = {
                            scope.launch {
                                dao.delete(plan)
                            }
                        }
                    )
                }

            }
        }
    }


    if (showAddDialog) {
        AddSubjectDialog(
            onDismiss = { },
            onSave = { subject, dateMillis, timeMillis ->

                val calendar = Calendar.getInstance().apply {
                    timeInMillis = dateMillis
                    val timeCal = Calendar.getInstance().apply {
                        timeInMillis = timeMillis
                    }
                    set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY))
                    set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE))
                    set(Calendar.SECOND, 0)
                }

                val triggerTime = calendar.timeInMillis

                if (triggerTime <= System.currentTimeMillis()) {
                    Toast.makeText(context, "Please select a future time", Toast.LENGTH_SHORT).show()
                    return@AddSubjectDialog
                }

                scope.launch {
                    dao.insert(
                        StudyPlanEntity(
                            subject = subject,
                            dateMillis = dateMillis,
                            timeMillis = timeMillis
                        )
                    )
                }

                ReminderScheduler.schedule(
                    context = context,
                    triggerTime = triggerTime,
                    subject = subject
                )

                showAddDialog = false
            }
        )
    }
}

@Composable
fun SubjectPlanCard(
    subject: String,
    date: String,
    time: String,
    onDelete: () -> Unit
) {
    val accentColor = getSubjectColor(subject)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row {

            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(accentColor)
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = accentColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = getSubjectEmoji(subject),
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                text = subject,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1F1F1F)
                            )
                        }
                    }

                    Row {
                        IconButton(onClick = onDelete) {
                            Icon(
                                Icons.Default.DeleteOutline,
                                contentDescription = "Delete",
                                tint = Color.Red.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CalendarToday,
                        null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(date, fontSize = 14.sp, color = Color.DarkGray)
                }

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.AccessTime,
                        null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(time, fontSize = 14.sp, color = Color.DarkGray)
                }
            }
        }
    }
}


fun getSubjectEmoji(subject: String): String {
    return when {
        subject.contains("math", true) -> "📐"
        subject.contains("physics", true) -> "⚡"
        subject.contains("chem", true) -> "🧪"
        subject.contains("bio", true) -> "🧬"
        subject.contains("english", true) -> "📖"
        subject.contains("history", true) -> "🏛️"
        else -> "📚"
    }
}


@Composable
fun getSubjectColor(subject: String): Color {
    return when {
        subject.contains("math", true) -> Color(0xFF4CAF50)
        subject.contains("physics", true) -> Color(0xFF2196F3)
        subject.contains("chem", true) -> Color(0xFF9C27B0)
        subject.contains("bio", true) -> Color(0xFF009688)
        subject.contains("english", true) -> Color(0xFFFF9800)
        subject.contains("history", true) -> Color(0xFF795548)
        else -> MaterialTheme.colorScheme.primary
    }
}

@Composable
fun AddSubjectDialog(
    onDismiss: () -> Unit,
    onSave: (String, Long, Long) -> Unit
) {
    var subject by remember { mutableStateOf("") }
    var dateText by remember { mutableStateOf("Select Date") }
    var timeText by remember { mutableStateOf("Select Time") }

    var dateMillis by remember { mutableStateOf(0L) }
    var timeMillis by remember { mutableStateOf(0L) }

    var subjectError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var timeError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("📘", fontSize = 20.sp)
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "Add study plan",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedTextField(
                    value = subject,
                    onValueChange = {
                        subject = it
                        subjectError = false
                    },
                    label = { Text("Subject name") },
                    isError = subjectError,
                    leadingIcon = { Icon(Icons.Default.Edit, null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (subjectError) {
                    Text(
                        "Subject cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                OutlinedButton(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, y, m, d ->
                                calendar.set(y, m, d)
                                dateMillis = calendar.timeInMillis
                                dateText =
                                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                        calendar.time
                                    )
                                dateError = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.CalendarToday, null)
                    Spacer(Modifier.width(10.dp))
                    Text(dateText)
                }

                if (dateError) {
                    Text(
                        "Please select a date",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                OutlinedButton(
                    onClick = {
                        TimePickerDialog(
                            context,
                            { _, h, min ->
                                calendar.set(Calendar.HOUR_OF_DAY, h)
                                calendar.set(Calendar.MINUTE, min)
                                timeMillis = calendar.timeInMillis
                                timeText = SimpleDateFormat(
                                    "hh:mm a",
                                    Locale.getDefault()
                                ).format(calendar.time)
                                timeError = false
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.AccessTime, null)
                    Spacer(Modifier.width(10.dp))
                    Text(timeText)
                }

                if (timeError) {
                    Text(
                        "Please select a time",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Cancel", color = Color.White)
                    }

                    Button(
                        onClick = {
                            subjectError = subject.isBlank()
                            dateError = dateMillis == 0L
                            timeError = timeMillis == 0L

                            if (!subjectError && !dateError && !timeError) {
                                onSave(subject, dateMillis, timeMillis)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Save", color = Color.White)
                    }
                }
            }
        }
    }
}

fun formatDate(millis: Long): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(millis))

fun formatTime(millis: Long): String =
    SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(millis))

@Preview(showBackground = true)
@Composable
fun StudyPlannerPreview() {
    StudyMateTheme {
        SubjectPlannerScreen(navController = rememberNavController())
    }
}
