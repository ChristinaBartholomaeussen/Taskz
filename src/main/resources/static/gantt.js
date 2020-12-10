google.charts.load('current', {'packages':['gantt']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
	const data = new google.visualization.DataTable();

	data.addColumn('string', 'Task ID');
	data.addColumn('string', 'Task Name');
	data.addColumn('string', 'Resource');
	data.addColumn('date', 'Start Date');
	data.addColumn('date', 'End Date');
	data.addColumn('number', 'Duration');
	data.addColumn('number', 'Percent Complete');
	data.addColumn('string', 'Dependencies');

	const projectStartdate = new Date(project.startDate);
	const projectDeadline = new Date(project.deadline);

	let totalAmountOfTasksInProject = 0;
	let completedTasksInProject = 0;
	let projectCompletion = 0;

	for(let subproject = 0; subproject < subprojects.length;subproject++)
	{
		let tasks = subprojects[subproject].taskList;

		totalAmountOfTasksInProject += tasks.length;

		for(let task = 0; task < tasks.length;task++)
			if(tasks[task].status === "COMPLETED")
				completedTasksInProject++;
	}

	if(totalAmountOfTasksInProject != 0)
		projectCompletion = (completedTasksInProject * 100) / totalAmountOfTasksInProject;

	data.addRows
	([
		[project.projectId.toString(),'[ ' + project.name + ' ]',null,projectStartdate,projectDeadline,null,projectCompletion,null]
	]);

	for (let i = 0; i < subprojects.length; i++) {

		const subprojectStartDate = new Date(subprojects[i].subprojectStartDate);
		const deadline = new Date(subprojects[i].subprojectDeadline);
		const tasks = subprojects[i].taskList;

		let subprojectCompletion = 0;
		let completedTasks = 0;

		for(let task = 0; task < tasks.length;task++)
			if(tasks[task].status === 'COMPLETED')
				completedTasks++;

		if(tasks.length != 0)
			subprojectCompletion = (completedTasks * 100) / tasks.length;

		data.addRows
		([
			[subprojects[i].subprojectId.toString(), 'SUBPROJECT: ' + subprojects[i].subprojectName,  null, subprojectStartDate, deadline, null, subprojectCompletion, null]
		]);

		for(let task = 0; task < tasks.length; task++)
		{
			let taskDeadline = Date.parse(tasks[task].deadline);
			let taskEstimatedStartDate = new Date(taskDeadline);
			taskEstimatedStartDate.setDate(taskEstimatedStartDate.getDate() - tasks[task].estimatedTime / 8);

			let subStartdate = new Date(subprojectStartDate);

			if(taskEstimatedStartDate < subStartdate)
			{
				taskEstimatedStartDate = subStartdate;
			}

			let taskCompletion = tasks[task].status == "ACTIVE" ? 0 : 100;

			data.addRows
			([
				[tasks[task].taskId.toString(), 'TASK: ' + tasks[task].taskName, subprojects[i].subprojectName, new Date(taskEstimatedStartDate), new Date(taskDeadline), null, taskCompletion, null]
			]);
		}
	}

	const options = {
		gantt:
			{
				criticalPathEnabled: false,
				sortTasks: false
			},
		hAxis:
			{
				format: 'M/d/yy',
				gridlines: {count: 15}
			},
		height: data.getNumberOfRows() * 50,
		width: 1000
	};

	const chart = new google.visualization.Gantt(document.getElementById('chart_div'));
	chart.draw(data, options);
}
