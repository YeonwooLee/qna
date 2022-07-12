package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }

//    @Autowired 생성자 하나니까 autowired 생략 가능
//    public ValidationItemControllerV2(ItemRepository itemRepository, ItemValidator itemValidator) {
//        this.itemRepository = itemRepository;
//        this.itemValidator = itemValidator;
//    }





    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())){ //글자가 없다
            bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수 입니다."));
        }
        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>1000000){
            bindingResult.addError(new FieldError("item","price","가격은 1,000원 이상 1,000,000이하 입니다"));
        }
        if(item.getQuantity()==null||item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다"));
        }

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }

        }


        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }



        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())){ //글자가 없다
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품 이름은 필수 입니다."));
        }
        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1,000원 이상 1,000,000이하 입니다"));
        }
        if(item.getQuantity()==null||item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,null,null,"수량은 최대 9,999 까지 허용합니다"));
        }

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }

        }


        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }



        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName={}",bindingResult.getObjectName());
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())){ //글자가 없다
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName","required.default"} ,null,"상품이름오류디폴트"));
        }
        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price","range.item.price2"},new Object[]{1000,10000},"가격은 1,000원 이상 1,000,000이하 입니다 디폴트"));
        }
        if(item.getQuantity()==null||item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999},"수량은 최대 9,999 까지 허용합니다 default"));
        }

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값(default) = " + resultPrice));
            }

        }


        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }



        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName={}",bindingResult.getObjectName());
        //검증 로직
        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

//        if (!StringUtils.hasText(item.getItemName())){ //글자가 없다
//            //명시적인 이름 값을 거절할꺼야
//            bindingResult.rejectValue("itemName","required"); //required만 써놓으면 required.item.itemName(자기 객체, 자기필드) 찾아감
//            //자기객체는 매개변수 순서가 자기 바로 앞인 것
//        }
        //공백체크는 간단하게 표현할 수 있다
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName","required");

        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>1000000){
            bindingResult.rejectValue("price","range",new Object[]{1000,1000000},null);//error코드 range만 써놓으면 range.item.price 찾아감
            bindingResult.rejectValue("price","range",new Object[]{2000,1000000},null);//error코드 range만 써놓으면 range.item.price 찾아감
            bindingResult.rejectValue("price","range",new Object[]{3000,1000000},null);//error코드 range만 써놓으면 range.item.price 찾아감
        }
        if(item.getQuantity()==null||item.getQuantity()>=9999){
            bindingResult.rejectValue("quantity","max",new Object[]{9999},null); //max만 써놓으면 ..
        }

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }

        }


        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }



        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        boolean supports = itemValidator.supports(item.getClass());
        if(supports) itemValidator.validate(item,bindingResult);

        log.info("objectName={}",bindingResult.getObjectName());
        //검증 로직
        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }



        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    //@Validated를 넣으면 자동으로 검증한다
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        log.info("objectName={}",bindingResult.getObjectName());
        //검증 로직
        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "validation/v2/addForm";
        }



        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

