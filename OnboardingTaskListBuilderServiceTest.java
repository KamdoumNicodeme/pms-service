import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { ICaseDocument } from '...'; // ton import existant

@Component({
  selector: 'documents',
  standalone: true,
  templateUrl: './documents.html',
  styleUrl: './documents.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DocumentsComponent {

  readonly documents = input.required<readonly ICaseDocument[]>();

  readonly validateDocument = output<ICaseDocument>();
  readonly rejectDocument = output<ICaseDocument>();
  readonly downloadDocument = output<ICaseDocument>();

  protected validate(document: ICaseDocument): void {
    this.validateDocument.emit(document);
  }

  protected reject(document: ICaseDocument): void {
    this.rejectDocument.emit(document);
  }

  protected download(document: ICaseDocument): void {
    this.downloadDocument.emit(document);
  }
}
