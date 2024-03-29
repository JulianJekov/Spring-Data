package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.TasksDtos.TaskImportDto;
import softuni.exam.models.dto.TasksDtos.TaskImportRootDto;
import softuni.exam.models.dto.TasksDtos.TaskViewDto;
import softuni.exam.models.entity.*;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";

    private final TasksRepository tasksRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final CarsRepository carsRepository;

    @Autowired
    public TasksServiceImpl(TasksRepository tasksRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil, MechanicsRepository mechanicsRepository, PartsRepository partsRepository, CarsRepository carsRepository) {
        this.tasksRepository = tasksRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.mechanicsRepository = mechanicsRepository;

        this.partsRepository = partsRepository;
        this.carsRepository = carsRepository;
    }

    @Override
    public boolean areImported() {
        return this.tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Paths.TASKS_XML_PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        final TaskImportRootDto taskImportRootDto =
                this.xmlParser.convertFromFile(TASKS_FILE_PATH, TaskImportRootDto.class);

        return taskImportRootDto.getTasks().stream()
                .map(this::importTask)
                .collect(Collectors.joining("\n"));
    }

    private String importTask(TaskImportDto taskImportDto) {
        final boolean isValid = this.validatorUtil.isValid(taskImportDto);

        final Optional<Mechanic> optionalMechanic =
                this.mechanicsRepository.findByFirstName(taskImportDto.getMechanic().getFirstName());

        if (!isValid || optionalMechanic.isEmpty()) {
            return Messages.INVALID_TASK;
        }
        final Optional<Car> car = this.carsRepository.findById(taskImportDto.getCar().getId());
        final Optional<Part> part = this.partsRepository.findById(taskImportDto.getPart().getId());

        final Task task = this.modelMapper.map(taskImportDto, Task.class);

        task.setMechanic(optionalMechanic.get());
        task.setCar(car.get());
        task.setPart(part.get());

        this.tasksRepository.saveAndFlush(task);
        return taskImportDto.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<Task> tasks =
                this.tasksRepository.findByCarCarTypeOrderByPriceDesc(CarType.coupe);

        return tasks.stream()
                .map(t -> this.modelMapper.map(t, TaskViewDto.class))
                .map(TaskViewDto::toString)
                .collect(Collectors.joining("\n"));
    }
}
