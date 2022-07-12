package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;

//    @Autowired 생성자 하나니까 autowired 생략 가능
//    public ValidationItemControllerV2(ItemRepository itemRepository, ItemValidator itemValidator) {
//        this.itemRepository = itemRepository;
//        this.itemValidator = itemValidator;
//    }


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    //@PostMapping("/add")
    //@Validated를 넣으면 자동으로 검증한다
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //오브젝트 에러는 이런식으로 직접 짜주는 게 낫다
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }

        }
        log.info("objectName={}", bindingResult.getObjectName());
        //검증 로직
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }


        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }
    @PostMapping("/add")
    //@Validated를 넣으면 자동으로 검증한다
    public String addItem2(@Validated(value = SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //오브젝트 에러는 이런식으로 직접 짜주는 게 낫다
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }

        }
        log.info("objectName={}", bindingResult.getObjectName());
        //검증 로직
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }


        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    //@PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute Item item, BindingResult bindingResult) {

        //오브젝트 에러는 이런식으로 직접 짜주는 게 낫다
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }

        }
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }
    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @Validated(value = UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult) {

        //오브젝트 에러는 이런식으로 직접 짜주는 게 낫다
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }

        }
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

